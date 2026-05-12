package com.example.travanalysserver.service.schedules;

import com.example.travanalysserver.controller.RankHorseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class RankedHorseScheduler {

    private final RankHorseController controller;
    private static final Logger logger =
            LoggerFactory.getLogger(RankedHorseScheduler.class);

    public RankedHorseScheduler(RankHorseController controller) {
        this.controller = controller;
    }

    // private final ReentrantLock lock = new ReentrantLock();
    //@Scheduled(cron = "0 0 12-23 * * *", zone = "Europe/Stockholm") //Skulle kunna ändra till fixeddELAY OCH ETT LOCK? Se ovan.
    public void runEveryFiveMinutes() {
        logger.info("Hämtar uppdaterd data ifrån GameChanger");
        controller.saveAllRanked();
    }
}
