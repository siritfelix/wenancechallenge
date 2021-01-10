package wenance.challenge.apichallemge.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import wenance.challenge.apichallemge.persistence.Dto.MetricAvg;
import wenance.challenge.apichallemge.persistence.model.BtcUsdPrice;
import wenance.challenge.apichallemge.persistence.repository.BtcUsdPriceRepository;
import wenance.challenge.apichallemge.service.impl.BtcUsdPriceServiceImpl;

public class BtcUsdPriceServiceTest {
    @InjectMocks
    private BtcUsdPriceServiceImpl btcUsdPriceServiceImpl;
    @Mock
    private BtcUsdPriceRepository btcUsdPriceRepository;

    public MockMvc mvc;

    @BeforeEach
    public void config() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(btcUsdPriceServiceImpl).build();
    }

    @Test
    public void savePrice() {
        BtcUsdPrice btcUsdPrice = new BtcUsdPrice(1, "10", null);
        try {
            when(btcUsdPriceRepository.save(btcUsdPrice)).thenReturn(btcUsdPrice);
        } catch (Exception e) {

        }
        BtcUsdPrice btcUsdPriceResult = btcUsdPriceServiceImpl.savePrice(btcUsdPrice);

        assertEquals(btcUsdPriceResult.getId(), 1);

    }

    @Test
    public void getPrice() {
        BtcUsdPrice btcUsdPrice = new BtcUsdPrice(1, "10", null);
        Optional<BtcUsdPrice> bOptional = Optional.of(btcUsdPrice);
        bOptional.of(btcUsdPrice);

        try {
            when(btcUsdPriceRepository.findById(1)).thenReturn(bOptional);
        } catch (Exception e) {

        }
        BtcUsdPrice btcUsdPriceResult = btcUsdPriceServiceImpl.getPrice(1).get();

        assertEquals(btcUsdPriceResult.getId(), 1);

    }

    @Test
    public void getMetric() {
        List<BtcUsdPrice> btcUsdPrices = new ArrayList<>();
        btcUsdPrices.add(new BtcUsdPrice(1, "10", null));
        btcUsdPrices.add(new BtcUsdPrice(2, "10.2", null));
        btcUsdPrices.add(new BtcUsdPrice(3, "10.3", null));
        btcUsdPrices.add(new BtcUsdPrice(4, "10.5", null));
        btcUsdPrices.add(new BtcUsdPrice(5, "20", null));
        try {
            when(btcUsdPriceRepository.findAll()).thenReturn(btcUsdPrices);
        } catch (Exception e) {

        }
        MetricAvg metricAvg = btcUsdPriceServiceImpl.getMetric(1, 2);

        assertEquals(metricAvg.getAvg(), "10.1");
        assertEquals(metricAvg.getMaxPrice(), "20.0");
        assertEquals(metricAvg.getDifPorct(), "49.5 %");

    }
}
