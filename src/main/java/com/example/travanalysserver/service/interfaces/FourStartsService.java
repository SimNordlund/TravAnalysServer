package com.example.travanalysserver.service.interfaces;

import com.example.travanalysserver.dto.completehorse.CompleteHorseDTO;
import com.example.travanalysserver.dto.starts.FourStartsDTO;
import com.example.travanalysserver.entity.CompleteHorse;
import com.example.travanalysserver.entity.FourStarts;

import java.io.IOException;
import java.util.List;

public interface FourStartsService {

    FourStartsDTO findFourStartsSingleData (Long completeHorseId);
    //List<FourStartsDTO> findCompleteHorseByCompetitionId(Long completeHorseId);

    String saveDownCompleteFourStartsToDB(FourStarts[] fourStarts);
    FourStarts[] getFourStartsFromJsonFile() throws IOException;
}
