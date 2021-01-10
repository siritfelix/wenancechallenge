package wenance.challenge.apichallemge.service;

import reactor.core.publisher.Mono;
import wenance.challenge.apichallemge.persistence.Dto.BtcUsdDto;

public interface ClientService {

    public Mono<BtcUsdDto> getPrice();
}
