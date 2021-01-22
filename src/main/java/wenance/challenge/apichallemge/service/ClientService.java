package wenance.challenge.apichallemge.service;

import reactor.core.publisher.Mono;

public interface ClientService {

    public Mono<String> getPrice();
}
