package com.example.travanalysserver.service.interfaces;

import com.example.travanalysserver.dto.completehorse.CompleteHorseDTO;
import com.example.travanalysserver.entity.CompleteHorse;
import com.example.travanalysserver.entity.Lap;

import java.io.IOException;
import java.util.List;

public interface CompleteHorseService {
    List<CompleteHorseDTO> findCompleteHorseByCompetitionId(Long competitionId);

    String saveDownCompleteHorsesToDB(CompleteHorse[] completeHorses);
    CompleteHorse[] getCompleteHorsesFromJsonFile() throws IOException;
}
