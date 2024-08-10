package com.example.travanalysserver.service.impl;

import com.example.travanalysserver.dto.lap.LapDTO;
import com.example.travanalysserver.entity.Lap;
import com.example.travanalysserver.repository.LapRepo;
import com.example.travanalysserver.service.interfaces.LapService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LapServiceImpl implements LapService {

    LapRepo lapRepo;

    @Override
    public List<LapDTO> findAllByCompetition_Id(Long competitionId){
        List <Lap> tempList = lapRepo.findAllByCompetition_Id(competitionId);
        List <LapDTO> lapsByFKList = tempList.stream().map(e -> LapToLapDTO(e)).toList();
        return lapsByFKList;
    }

    public LapDTO LapToLapDTO (Lap lap){
        return LapDTO.builder()
                .id(lap.getId())
                .nameOfLap(lap.getNameOfLap())
                .build();
    }



}
