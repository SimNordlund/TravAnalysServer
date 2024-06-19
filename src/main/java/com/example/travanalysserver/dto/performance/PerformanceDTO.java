package com.example.travanalysserver.dto.performance;

import com.example.travanalysserver.entity.Horse;
import com.example.travanalysserver.entity.Lap;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //getters + setters
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PerformanceDTO {
    private Long id;
    private Horse horse;
    private Lap lap;
    private int number;
    private double odds;
}
