package wenance.challenge.apichallemge.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;
import wenance.challenge.apichallemge.persistence.Dto.BtcUsdDto;
import wenance.challenge.apichallemge.persistence.model.BtcUsdPrice;
import wenance.challenge.apichallemge.service.BtcUsdPriceService;
import wenance.challenge.apichallemge.service.ClientService;

@Service
@Log4j2
public class ClientServiceImpl implements ClientService {
    @Autowired
    private WebClient regisClient;
    @Autowired
    private BtcUsdPriceService btcUsdPriceService;

    @Override
    public Mono<String> getPrice() {

        Mono<String> monobtcUsdDto = this.regisClient.get().retrieve().bodyToMono(String.class);
        ObjectMapper mapper = new ObjectMapper();

        monobtcUsdDto.subscribe(s -> {
            try {
                log.info(s);
                BtcUsdDto btcUsdDto = mapper.readValue(s, BtcUsdDto.class);
                BtcUsdPrice btcUsdPrice = new BtcUsdPrice();
                btcUsdPrice.setPrice(btcUsdDto.getLprice());
                Mono<BtcUsdPrice> bMono = btcUsdPriceService.savePrice(btcUsdPrice);
                bMono.subscribe(m -> log.info(m.getId()));
                log.info("Consulta realizada");
            } catch (Exception e) {

            }
        });
        return monobtcUsdDto;
    }

}
