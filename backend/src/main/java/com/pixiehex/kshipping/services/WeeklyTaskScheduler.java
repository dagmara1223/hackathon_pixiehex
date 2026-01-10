package com.pixiehex.kshipping.services;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableScheduling
@Component
public class WeeklyTaskScheduler {

    private final BatchService batchService;
    private SingleOrderService singleOrderService;

    public WeeklyTaskScheduler(BatchService batchService, SingleOrderService singleOrderService) {
        this.batchService = batchService;
        this.singleOrderService = singleOrderService;
    }

    // Every Monday at 00:00
    @Scheduled(cron = "0 0 0 * * MON")
    public void runWeeklyBatching() {
        batchService.processBatching();
        singleOrderService.changeUnpaidToCancelled();
    }
}
