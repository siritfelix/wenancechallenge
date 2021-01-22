package wenance.challenge.apichallemge.persistence.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import wenance.challenge.apichallemge.persistence.model.BtcUsdPrice;

public interface BtcUsdPriceRepositoryReactive extends R2dbcRepository<BtcUsdPrice, Integer> {

}
