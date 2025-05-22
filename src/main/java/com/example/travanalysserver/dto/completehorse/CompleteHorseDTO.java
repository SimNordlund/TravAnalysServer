package com.example.travanalysserver.dto.completehorse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompleteHorseDTO {
    private Long id;
    private int numberOfCompleteHorse;
    private String nameOfCompleteHorse;
}
