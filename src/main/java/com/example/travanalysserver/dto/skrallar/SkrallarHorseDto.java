package com.example.travanalysserver.dto.skrallar;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SkrallarHorseDto {
    private Long horseId;
    private int numberOfHorse;
    private String nameOfHorse;
    private int analys;
    private int fart;
    private int styrka;
    private int klass;
    private int prispengar;
    private String lap;
    private String nameOfTrack;
}
