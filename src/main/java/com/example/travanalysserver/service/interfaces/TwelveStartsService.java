package com.example.travanalysserver.service.interfaces;

import com.example.travanalysserver.dto.starts.TwelveStartsDTO;

public interface TwelveStartsService {
    TwelveStartsDTO findTwelveStartsSingleData (Long completeHorseId);
}
