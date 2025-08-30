package com.example.travanalysserver.service.interfaces;

import com.example.travanalysserver.dto.starts.EightStartsDTO;

public interface EightStartsService {
    EightStartsDTO findEightStartsSingleData (Long completeHorseId);
}
