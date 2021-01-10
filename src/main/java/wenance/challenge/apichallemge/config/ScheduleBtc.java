package wenance.challenge.apichallemge.config;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import lombok.extern.log4j.Log4j2;
import wenance.challenge.apichallemge.service.ClientService;

@Configuration
@EnableScheduling
@Log4j2
public class ScheduleBtc {

    @Autowired
    private ClientService clientService;

    @Scheduled(cron = "0/10 * * 1/1 * *")
    public void runTask() {
        log.info("Consulting price ....", LocalDateTime.now());
        try {
            clientService.getPrice();
        } catch (Exception e) {
            log.error("Error .......", e.getMessage());
        }

    }
}
