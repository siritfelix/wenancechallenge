package wenance.challenge.apichallemge.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import wenance.challenge.apichallemge.persistence.Dto.MetricAvg;
import wenance.challenge.apichallemge.persistence.model.BtcUsdPrice;
import wenance.challenge.apichallemge.service.ClientService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class BtcUsdControllerTest {
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private ClientService clientService;

    @BeforeEach
    public void config() {
        clientService.getPrice().block();
        clientService.getPrice().block();
        clientService.getPrice().block();
    }

    @Test
    void getPrice() {
        this.webTestClient.get().uri("/price/1").exchange().expectStatus().isOk().expectBody(BtcUsdPrice.class)
                .value(btc -> assertEquals(btc.getId(), 1));
        this.webTestClient.get().uri("/price/2").exchange().expectStatus().isOk().expectBody(BtcUsdPrice.class)
                .value(btc -> assertEquals(btc.getId(), 2));
        this.webTestClient.get().uri("/price/3").exchange().expectStatus().isOk().expectBody(BtcUsdPrice.class)
                .value(btc -> assertEquals(btc.getId(), 3));
        this.webTestClient.get().uri("/price/100").exchange().expectStatus().isNotFound();
    }

    @Test
    void getPriceAvr() {
        this.webTestClient.get().uri("/price/1/2").exchange().expectStatus().isOk().expectBody(MetricAvg.class)
                .value(btc -> assertEquals(btc.getAvg().getClass(), String.class));
        this.webTestClient.get().uri("/price/1/1").exchange().expectStatus().isNotFound();
        this.webTestClient.get().uri("/price/0/X").exchange().expectStatus().is5xxServerError();
    }

    @Test
    void getAllPrice() {
        this.webTestClient.get().uri("/price/all_prices").exchange().expectStatus().isOk().expectBody(List.class)
                .value(btc -> assertEquals(btc.size() > 3 || btc.size() < 6, true));

    }
}
