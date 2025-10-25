package com.example.travanalysserver.service.schedules;

import com.example.travanalysserver.controller.RankHorseController;
import com.example.travanalysserver.service.impl.PrimaryDbCleanupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RankedHorseScheduler {

    private final RankHorseController controller;
    private final PrimaryDbCleanupService cleanup;

    private static final Logger logger =
            LoggerFactory.getLogger(RankedHorseScheduler.class);

    public RankedHorseScheduler(RankHorseController controller,
                                PrimaryDbCleanupService cleanup) {
        this.controller = controller;
        this.cleanup = cleanup;
    }

    // private final ReentrantLock lock = new ReentrantLock();
    //@Scheduled(cron = "0 0/10 * * * *") //Skulle kunna ändra till fixeddELAY OCH ETT LOCK? Se ovan.
    public void runEveryFiveMinutes() {
        logger.info("Hämtar uppdaterd data ifrån GameChanger");
       // cleanup.truncateAllExceptEmailAndSyncMeta();
        controller.saveAllRanked();
    }
}