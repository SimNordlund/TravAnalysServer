package com.example.travanalysserver.dto.competition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data //getters + setters
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompetitionDTO {
    private Long id;
    private String nameOfCompetition;
}
