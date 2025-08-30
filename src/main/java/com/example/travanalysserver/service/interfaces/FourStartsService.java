package com.example.travanalysserver.service.interfaces;

import com.example.travanalysserver.dto.starts.FourStartsDTO;
import com.example.travanalysserver.entity.FourStarts;

import java.io.IOException;

public interface FourStartsService {

    FourStartsDTO findFourStartsSingleData (Long completeHorseId);
    String saveDownCompleteFourStartsToDB(FourStarts[] fourStarts);
    FourStarts[] getFourStartsFromJsonFile() throws IOException;
}
