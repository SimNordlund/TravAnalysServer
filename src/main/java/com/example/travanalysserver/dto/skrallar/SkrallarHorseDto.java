package com.example.travanalysserver.dto.skrallar;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SkrallarHorseDto {
    private Long horseId;
    private int numberOfHorse;
    private String nameOfHorse;
    private int analys;
    private BigDecimal roiTotalt;
    private BigDecimal roiVinnare;
    private BigDecimal roiPlats;
    private BigDecimal roiTrio;
    private int resultat;
    private int tips;
    private String lap;
    private String nameOfTrack;
}
