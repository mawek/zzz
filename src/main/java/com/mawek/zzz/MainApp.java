package com.mawek.zzz;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mawek.zzz.model.Loan;
import com.mawek.zzz.service.MarketplaceService;
import com.mawek.zzz.service.processor.LoanProcessor;
import org.slf4j.Logger;
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

import static org.slf4j.LoggerFactory.getLogger;

@SpringBootApplication
public class MainApp {

    private static Logger logger = getLogger(MainApp.class);


    @Autowired
    private MarketplaceService marketplaceService;

    @Autowired
    private LoanProcessor newLoansProcessor;

    // datetime of last check of loans
    private ZonedDateTime fromDatePublished = ZonedDateTime.now();


    public static void main(String[] args) {
        logger.info("Starting the zzz application...");
        SpringApplication.run(MainApp.class, args);
    }

    @Scheduled(fixedDelayString = "${scheduler.rate.ms}")
    public void processNewLoans() {
        logger.debug("action=fetching_new_loans fromDatePublished={}", fromDatePublished);

        final List<Loan> loans = marketplaceService.getLoans(fromDatePublished);

        logger.debug("action=new_loans_fetched size={}", loans.size());

        logger.debug("action=processing_new_loans");
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
