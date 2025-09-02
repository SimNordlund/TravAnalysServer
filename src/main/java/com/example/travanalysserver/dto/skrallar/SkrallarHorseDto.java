package com.example.travanalysserver.dto.skrallar;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data @NoArgsConstructor @AllArgsConstructor
public class SkrallarHorseDto {
    private Long trackId;
    private String nameOfTrack;

    private Long competitionId;
    private String nameOfCompetition;

    private Long lapId;
    private String lap;

    private Long completeHorseId;
    private Integer numberOfHorse;
    private String nameOfHorse;

    private Integer tips;
    private Integer analys;
    private Integer resultat;

    private BigDecimal roiTotalt;
    private BigDecimal roiVinnare;
    private BigDecimal roiPlats;
}