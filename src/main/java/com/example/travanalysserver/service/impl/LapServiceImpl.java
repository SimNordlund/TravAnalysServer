package com.example.travanalysserver.service.impl;

import com.example.travanalysserver.dto.lap.LapDTO;
import com.example.travanalysserver.entity.Lap;
import com.example.travanalysserver.repository.LapRepo;
import com.example.travanalysserver.service.interfaces.LapService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class LapServiceImpl implements LapService {

    private LapRepo lapRepo;

    @Override
    public List<LapDTO> findAllByCompetition_Id(Long competitionId){
        List <Lap> tempList = lapRepo.findAllByCompetition_Id(competitionId);
        tempList.sort(Comparator.comparingInt(l -> Integer.parseInt(l.getNameOfLap())));
        List <LapDTO> lapsByFKList = tempList.stream().map(e -> LapToLapDTO(e)).toList();
        return lapsByFKList;
    }

    public LapDTO LapToLapDTO (Lap lap){
        return LapDTO.builder()
                .id(lap.getId())
                .nameOfLap(lap.getNameOfLap())
                .build();
    }

    @Override
    public String saveDownLapsToDB(Lap[] laps) {
        List<Lap> lapsList = Arrays.stream(laps).toList();
        lapRepo.saveAll(lapsList);
        return "Allt gick bra med Laps";
    }

    @Override
    public Lap[] getLapsFromJsonFile() throws IOException{
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        File jsonFile = new File("C:\\Users\\TaraR\\IdeaProjects\\TravAnalysServer\\src\\main\\java\\com\\example\\travanalysserver\\data\\laps.json");

        Lap[] lapsFromJson = objectMapper.readValue(jsonFile, Lap[].class);
        return lapsFromJson;
    }

}
