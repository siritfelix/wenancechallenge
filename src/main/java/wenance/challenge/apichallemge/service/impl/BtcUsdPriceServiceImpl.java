package wenance.challenge.apichallemge.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import wenance.challenge.apichallemge.exceptions.NotFoundException;
import wenance.challenge.apichallemge.persistence.Dto.MetricAvg;
import wenance.challenge.apichallemge.persistence.model.BtcUsdPrice;
import wenance.challenge.apichallemge.persistence.repository.BtcUsdPriceRepository;
import wenance.challenge.apichallemge.service.BtcUsdPriceService;

@Service
public class BtcUsdPriceServiceImpl implements BtcUsdPriceService {
    @Autowired
    private BtcUsdPriceRepository btcUsdPriceRepository;

    @Override
    public BtcUsdPrice savePrice(BtcUsdPrice btcUsdPrice) {

        return btcUsdPriceRepository.save(btcUsdPrice);

    }

    @Override
    public Optional<BtcUsdPrice> getPrice(Integer timestamp) {
        return btcUsdPriceRepository.findById(timestamp);

    }

    @Override
    public MetricAvg getMetric(Integer timestamp1, Integer timestamp2) {
        List<BtcUsdPrice> btcUsdPrices = btcUsdPriceRepository.findAll();
        MetricAvg metricAvg = new MetricAvg();
        double avg = 0;
        if (btcUsdPrices.stream().filter(p -> (p.getId() == timestamp1 || p.getId() == timestamp2)).count() == 2) {

            avg = btcUsdPrices.stream().filter(p -> (p.getId() == timestamp1 || p.getId() == timestamp2))
                    .map(BtcUsdPrice::getPrice).map(Double::parseDouble).mapToDouble(p -> p).average().getAsDouble();

            double max = btcUsdPrices.stream().map(BtcUsdPrice::getPrice).map(Double::parseDouble).mapToDouble(p -> p)
                    .max().getAsDouble();
            double difPorct = (max - avg) * 100 / max;
            
            metricAvg.setMaxPrice(String.valueOf(max));
            metricAvg.setAvg(String.valueOf(avg));
            metricAvg.setDifPorct(String.valueOf(difPorct).concat(" %"));
            return metricAvg;
        } else {
            throw new NotFoundException("No timestamp found!!");
        }

    }

    @Override
    public List<BtcUsdPrice> findAll() {

        return btcUsdPriceRepository.findAll();

    }

}
