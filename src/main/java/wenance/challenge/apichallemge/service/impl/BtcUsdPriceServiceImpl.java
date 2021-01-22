package wenance.challenge.apichallemge.service.impl;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import wenance.challenge.apichallemge.persistence.Dto.MetricAvg;
import wenance.challenge.apichallemge.persistence.model.BtcUsdPrice;
import wenance.challenge.apichallemge.persistence.repository.BtcUsdPriceRepositoryReactive;
import wenance.challenge.apichallemge.service.BtcUsdPriceService;

@Service
@RequiredArgsConstructor
public class BtcUsdPriceServiceImpl implements BtcUsdPriceService {
    Mono<MetricAvg> metricAvgM;
    private final BtcUsdPriceRepositoryReactive btcUsdPriceRepository;

    @Override
    public Mono<BtcUsdPrice> savePrice(BtcUsdPrice btcUsdPrice) {

        return btcUsdPriceRepository.save(btcUsdPrice);

    }

    @Override
    public Mono<BtcUsdPrice> getPrice(Integer timestamp) {
        return btcUsdPriceRepository.findById(timestamp);

    }

    @Override
    public Mono<MetricAvg> getMetric(Integer timestamp1, Integer timestamp2) {

        btcUsdPriceRepository.findAll().collectList().subscribe(l -> {
            if (l.stream().filter(p -> (p.getId() == timestamp1 || p.getId() == timestamp2)).count() == 2) {
                double avg = l.stream().filter(p -> (p.getId() == timestamp1 || p.getId() == timestamp2))
                        .map(BtcUsdPrice::getPrice).map(Double::parseDouble).mapToDouble(p -> p).average()
                        .getAsDouble();

                double max = l.stream().map(BtcUsdPrice::getPrice).map(Double::parseDouble).mapToDouble(p -> p).max()
                        .getAsDouble();

                this.metricAvgM = Mono.just(new MetricAvg(String.valueOf(avg), String.valueOf((max - avg) * 100 / max),
                        String.valueOf(max)));
            } else {
                this.metricAvgM = Mono.empty();

            }
        });
        return this.metricAvgM;
    }

    @Override
    public Flux<BtcUsdPrice> findAll() {

        return btcUsdPriceRepository.findAll();

    }

}
