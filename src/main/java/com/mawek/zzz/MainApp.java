package com.mawek.zzz;

import com.mawek.zzz.service.MarketplaceService;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainApp {


    private static final MarketplaceService marketplaceService = new MarketplaceService();


    public static void main(String[] args) {

        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> marketplaceService.getLoans().stream().forEach(loan -> System.out.println(loan)), 1, 5, TimeUnit.SECONDS);

    }
}
