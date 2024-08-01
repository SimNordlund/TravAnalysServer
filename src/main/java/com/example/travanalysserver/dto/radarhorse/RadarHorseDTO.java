package com.example.travanalysserver.dto.radarhorse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RadarHorseDTO {
    private Long id;
    private String name;

    private int valueOne;
    private int valueTwo;
    private int valueThree;
    private int valueFour;
    private int valueFive;

/*    public RadarHorse RadarHorseDtoToRadarHorse(RadarHorseDTO radarHorseDTO) {
        return RadarHorse.builder()
                .id(radarHorseDTO.getId())
                .name(radarHorseDTO.getName())
                .valueOne(radarHorseDTO.getValueOne())
                .valueTwo(radarHorseDTO.getValueTwo())
                .valueThree(radarHorseDTO.getValueThree())
                .valueFour(radarHorseDTO.getValueFour())
                .valueFive(radarHorseDTO.getValueFive())
                .build();
    }

    public RadarHorseDTO RadarHorseToRadarHorseDto(RadarHorse radarHorse) {
        return RadarHorseDTO.builder()
                .id(radarHorse.getId())
                .name(radarHorse.getName())
                .valueOne(radarHorse.getValueOne())
                .valueTwo(radarHorse.getValueTwo())
                .valueThree(radarHorse.getValueThree())
                .valueFour(radarHorse.getValueFour())
                .valueFive(radarHorse.getValueFive())
                .build();
    } */
}
