package wenance.challenge.apichallemge.service;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import wenance.challenge.apichallemge.persistence.Dto.MetricAvg;
import wenance.challenge.apichallemge.persistence.model.BtcUsdPrice;

@Service
public interface BtcUsdPriceService {

    public Mono<BtcUsdPrice> savePrice(BtcUsdPrice btcUsdPrice);

    public Mono<BtcUsdPrice> getPrice(Integer timestamp);

    public Mono<MetricAvg> getMetric(Integer timestamp1, Integer timestamp2);

    public Flux<BtcUsdPrice> findAll();
}
