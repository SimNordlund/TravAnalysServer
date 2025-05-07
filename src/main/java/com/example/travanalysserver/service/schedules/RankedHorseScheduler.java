package com.example.travanalysserver.service.schedules;

import com.example.travanalysserver.controller.RankHorseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RankedHorseScheduler {

    private final RankHorseController controller;

    private static final Logger logger =
            LoggerFactory.getLogger(RankedHorseScheduler.class);

    public RankedHorseScheduler(RankHorseController controller) {
        this.controller = controller;
    }

    @Scheduled(cron = "0 0/5 * * * *")
    public void runEveryFiveMinutes() {
        logger.info("Hämtar uppdaterd data ifrån GameChanger");
        controller.saveAllRanked();
    }
}
