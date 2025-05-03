package com.example.travanalysserver.entitysec;

import java.time.LocalDate;

public interface RankHorseView {
    Long      getId();
    Integer getDateRankedHorse(); //Ändra sen jao
    String    getTrackRankedHorse();
    String    getCompetitionRankedHorse();
    String    getLapRankedHorse();
    String    getNameRankedHorse();
    String    getAnalysRankedHorse();
    String    getTidRankedHorse();
    String    getPrestationRankedHorse();
    String    getMotstandRankedHorse();
}
