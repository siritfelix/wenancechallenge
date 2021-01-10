package wenance.challenge.apichallemge.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import wenance.challenge.apichallemge.persistence.Dto.MetricAvg;
import wenance.challenge.apichallemge.persistence.model.BtcUsdPrice;

@Service
public interface BtcUsdPriceService {

    public BtcUsdPrice savePrice(BtcUsdPrice btcUsdPrice);

    public Optional<BtcUsdPrice> getPrice(Integer timestamp);

    public MetricAvg getMetric(Integer timestamp1, Integer timestamp2);

    public List<BtcUsdPrice> findAll();
}
