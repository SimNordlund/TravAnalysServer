package com.example.travanalysserver.dto.track;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NameOfTrackDTO {
    private Long id;
    private String nameOfTrack;
}
