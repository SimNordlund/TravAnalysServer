package com.example.travanalysserver.service.interfaces;

import com.example.travanalysserver.dto.lap.LapDTO;
import com.example.travanalysserver.entity.Competition;
import com.example.travanalysserver.entity.Lap;

import java.io.IOException;
import java.util.List;

public interface LapService {

    List <LapDTO> findAllByCompetition_Id(Long competitionId);

    String saveDownLapsToDB(Lap[] laps);
    Lap[] getLapsFromJsonFile() throws IOException;
}
