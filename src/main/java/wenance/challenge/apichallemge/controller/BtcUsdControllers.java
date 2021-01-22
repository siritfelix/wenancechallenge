package wenance.challenge.apichallemge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import wenance.challenge.apichallemge.persistence.Dto.MetricAvg;
import wenance.challenge.apichallemge.persistence.model.BtcUsdPrice;
import wenance.challenge.apichallemge.service.BtcUsdPriceService;

@RestController
@RequestMapping(value = "/price")
public class BtcUsdControllers {

    @Autowired
    BtcUsdPriceService btcUsdPriceService;

    @GetMapping(value = "/{timestamp}")
    public Mono<ResponseEntity<BtcUsdPrice>> getPrice(@PathVariable Integer timestamp) {

        return btcUsdPriceService.getPrice(timestamp).map(r -> ResponseEntity.ok(r))
                .defaultIfEmpty(ResponseEntity.notFound().build());

    }

    @GetMapping(value = "/{timestamp1}/{timestamp2}")
    public Mono<ResponseEntity<MetricAvg>> getPriceAvr(@PathVariable Integer timestamp1,

            @PathVariable Integer timestamp2) {
        return btcUsdPriceService.getMetric(timestamp1, timestamp2).map(r -> ResponseEntity.ok(r))
                .defaultIfEmpty(ResponseEntity.notFound().build());
        
    }

    @GetMapping(value = "/all_prices")
    public Flux<BtcUsdPrice> getAllPrice() {
        return btcUsdPriceService.findAll();

    }

}
