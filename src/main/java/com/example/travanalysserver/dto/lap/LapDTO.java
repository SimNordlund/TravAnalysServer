package com.example.travanalysserver.dto.lap;

import com.example.travanalysserver.entity.Competition;
import com.example.travanalysserver.entity.Track;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LapDTO {
    private Long id;
    private String nameOfLap;
    private Competition competition;
}
