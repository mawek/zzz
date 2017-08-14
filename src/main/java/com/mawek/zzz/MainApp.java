package com.mawek.zzz;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mawek.zzz.model.Loan;
import com.mawek.zzz.service.processor.LoanProcessor;
import com.mawek.zzz.service.MarketplaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import java.time.ZonedDateTime;
import java.util.List;

@SpringBootApplication
public class MainApp {

    @Autowired
    private MarketplaceService marketplaceService;

    @Autowired
    private LoanProcessor newLoansProcessor;

    // datetime of last check of loans
    private ZonedDateTime fromDatePublished = ZonedDateTime.now();


    public static void main(String[] args) {
        SpringApplication.run(MainApp.class, args);
    }

    @Scheduled(fixedDelayString = "${scheduler.rate.ms}")
    public void printLoans() {

        final List<Loan> loans = marketplaceService.getLoans(fromDatePublished);

        // maybe some more reactive - async publish/subscriber processor if the rate is too big
        newLoansProcessor.processLoans(loans);

        if (loans.isEmpty()) {
            return;
        }

        fromDatePublished = loans.get(0).getDatePublished().plusNanos(1);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper().findAndRegisterModules();
    }

    @Configuration
    @ConditionalOnProperty(value = "scheduler.enabled")
    @EnableScheduling
    static class SchedulingConfiguration {

    }

}
