package com.example.travanalysserver.entitysec;

public interface RankHorseView {
        Long getId();
        Integer getDateRankedHorse();      // Changed!: Integer, not LocalDate
        String getTrackRankedHorse();
        String getLapRankedHorse();
        Integer getNr();
        String getNameRankedHorse();
        String getAnalysRankedHorse();
        String getTidRankedHorse();
        String getPrestationRankedHorse();
        String getMotstandRankedHorse();
}