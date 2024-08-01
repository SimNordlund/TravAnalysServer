package com.example.travanalysserver.dto.lap;

import com.example.travanalysserver.entity.Track;
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
    private Track track;
    private int number;
}
