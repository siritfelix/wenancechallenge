package wenance.challenge.apichallemge.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;
import wenance.challenge.apichallemge.persistence.Dto.BtcUsdDto;
import wenance.challenge.apichallemge.persistence.model.BtcUsdPrice;
import wenance.challenge.apichallemge.service.BtcUsdPriceService;
import wenance.challenge.apichallemge.service.ClientService;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private WebClient regisClient;
    @Autowired
    private BtcUsdPriceService btcUsdPriceService;

    @Override
    public Mono<BtcUsdDto> getPrice() {

        Mono<String> monobtcUsdDto = this.regisClient.get().retrieve().bodyToMono(String.class);

        String btcUsdDtoStr = monobtcUsdDto.block();
        ObjectMapper mapper = new ObjectMapper();
        BtcUsdDto btcUsdDto;
        try {
            btcUsdDto = mapper.readValue(btcUsdDtoStr, BtcUsdDto.class);
            BtcUsdPrice btcUsdPrice = new BtcUsdPrice();
            btcUsdPrice.setPrice(btcUsdDto.getLprice());
            btcUsdPriceService.savePrice(btcUsdPrice);
        } catch (Exception e) {
            btcUsdDto = new BtcUsdDto();
        }

        return Mono.just(btcUsdDto);
    }

}
