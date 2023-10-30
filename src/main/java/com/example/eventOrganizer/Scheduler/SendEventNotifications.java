package com.example.eventOrganizer.Scheduler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.eventOrganizer.Service.RewardService;
@Component
public class SendEventNotifications {

    @Autowired
    RewardService rewardService;


    // @Scheduled(cron = "0 0 1 4,8,12 * ?") // At midnight on the 1st of April, August, and December
    // @Scheduled(fixedRate = 30000)
    public void updateStatusOnScheduledDates() {
        // System.out.println("hanzala is running");
        rewardService.saveLeaderBoardHistoryService();
    }
   

}
