package wenance.challenge.apichallemge.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import wenance.challenge.apichallemge.persistence.model.BtcUsdPrice;
@Repository
public interface BtcUsdPriceRepository extends JpaRepository<BtcUsdPrice,Integer>{
    
}
