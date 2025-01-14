package com.example.travanalysserver.dto.completehorse;

import com.example.travanalysserver.entity.FourStarts;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //getters + setters
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompleteHorseDTOAnalys {
    private Long id;
    private String nameOfCompleteHorse;
    private int fourStartsAnalys;
}
