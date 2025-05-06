package com.example.travanalysserver.dto.completehorse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //getters + setters
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompleteHorseDTO {
    private Long id;
    private int numberOfCompleteHorse;
    private String nameOfCompleteHorse;
}
