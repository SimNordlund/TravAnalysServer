package com.example.travanalysserver.entitysec;

import java.time.LocalDateTime;

public interface RankHorseView {
        Long getId();
        Integer getDateRankedHorse();
        String getTrackRankedHorse();
        String getLapRankedHorse();
        Integer getNr();
        String getNameRankedHorse();
        String getAnalysRankedHorse();
        String getTidRankedHorse();
        String getPrestationRankedHorse();
        String getMotstandRankedHorse();
        String getPrispengarRankedHorse();
        LocalDateTime getUpdatedAt();
        String getTipsRankedHorse();
}