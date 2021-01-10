package wenance.challenge.apichallemge.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;
import wenance.challenge.apichallemge.exceptions.NotFoundException;
import wenance.challenge.apichallemge.persistence.Dto.MetricAvg;
import wenance.challenge.apichallemge.persistence.model.BtcUsdPrice;
import wenance.challenge.apichallemge.service.BtcUsdPriceService;

@RestController
@RequestMapping(value = "/price")
public class BtcUsdControllers {

    @Autowired
    private BtcUsdPriceService btcUsdPriceService;

    @GetMapping(value = "/{timestamp}")
    public Mono<ResponseEntity<?>> getPrice(@PathVariable Integer timestamp) {

        Optional<BtcUsdPrice> btcUsdPrice = btcUsdPriceService.getPrice(timestamp);
        if (btcUsdPrice.isPresent())
            return Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(btcUsdPrice.get()));
        else
            throw new NotFoundException("No timestamp found!!");

    }

    @GetMapping(value = "/{timestamp1}/{timestamp2}")
    public Mono<ResponseEntity<MetricAvg>> getPriceAvr(@PathVariable Integer timestamp1,
            @PathVariable Integer timestamp2) {
        return Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(btcUsdPriceService.getMetric(timestamp1, timestamp2)));
    }

    @GetMapping(value = "/all_prices")
    public Mono<ResponseEntity<List<BtcUsdPrice>>> getAllPrice() {
        return Mono
                .just(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(btcUsdPriceService.findAll()));
    }

}
