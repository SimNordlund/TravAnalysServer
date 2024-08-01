package com.example.travanalysserver.dto.horse;

import com.example.travanalysserver.entity.testing.Horse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //getters + setters
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HorseDTO {
    private Long id;
    private String name;

    public Horse HorseDtoToHorse(HorseDTO horseDTO) {
        return Horse.builder()
                .id(horseDTO.getId())
                .name(horseDTO.getName())
                .build();
    }

    public HorseDTO HorseToHorseDTO(Horse horse) {
        return HorseDTO.builder()
                .id(horse.getId())
                .name(horse.getName())
                .build();
    }
}



