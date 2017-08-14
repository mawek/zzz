package com.mawek.zzz;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mawek.zzz.model.Loan;
import com.mawek.zzz.service.MarketplaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import java.time.ZonedDateTime;
import java.util.List;

@SpringBootApplication
@EnableScheduling
public class MainApp {

    @Autowired
    private MarketplaceService marketplaceService;

    // datetime of last check of loans
    private ZonedDateTime lastCheck = ZonedDateTime.now();


    public static void main(String[] args) {
        SpringApplication.run(MainApp.class, args);
    }

    @Scheduled(fixedDelay = 5000)
    public void printLoans() {

        // TODO some reporter
        final List<Loan> loans = marketplaceService.getLoans(lastCheck);
        loans.stream().forEach(loan -> System.out.println(loan));

        // TODO it should be datePublished of latest loan + 1 ms
        lastCheck = ZonedDateTime.now();
    }

    // TODO error handler
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper().findAndRegisterModules();
    }

}
