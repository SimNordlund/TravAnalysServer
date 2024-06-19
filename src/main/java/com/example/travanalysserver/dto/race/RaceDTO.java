package com.example.travanalysserver.dto.race;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data //getters + setters
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RaceDTO {
    private Long id;
    private LocalDate date;
    private String location;
}
