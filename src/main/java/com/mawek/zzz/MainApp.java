package com.mawek.zzz;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mawek.zzz.service.MarketplaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class MainApp {


    @Autowired
    private MarketplaceService marketplaceService;


    public static void main(String[] args) {
        SpringApplication.run(MainApp.class, args);
    }
    
    @Scheduled(fixedDelay = 5000)
    public void printLoans() {
        marketplaceService.getLoans().stream().forEach(loan -> System.out.println(loan));
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

}
