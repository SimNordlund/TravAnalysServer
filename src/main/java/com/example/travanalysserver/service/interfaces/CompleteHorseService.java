package com.example.travanalysserver.service.interfaces;

import com.example.travanalysserver.dto.completehorse.CompleteHorseDTO;

import java.util.List;

public interface CompleteHorseService {
    List<CompleteHorseDTO> findCompleteHorseByCompetitionId(Long competitionId);
}
