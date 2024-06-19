package com.example.travanalysserver.dto.lap;

import com.example.travanalysserver.entity.Race;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //getters + setters
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LapDTO {
    private Long id;
    private Race race;
    private int number;
}
