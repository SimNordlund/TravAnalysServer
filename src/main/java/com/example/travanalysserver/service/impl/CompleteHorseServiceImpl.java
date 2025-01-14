package com.example.travanalysserver.service.impl;

import com.example.travanalysserver.dto.completehorse.CompleteHorseDTO;
import com.example.travanalysserver.dto.completehorse.CompleteHorseDTOAnalys;
import com.example.travanalysserver.entity.CompleteHorse;
import com.example.travanalysserver.entity.Lap;
import com.example.travanalysserver.repository.CompleteHorseRepo;
import com.example.travanalysserver.service.interfaces.CompleteHorseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class CompleteHorseServiceImpl implements CompleteHorseService {

    private CompleteHorseRepo completeHorseRepo;

    @Override
    public List<CompleteHorseDTO> findCompleteHorseByCompetitionId(Long competitionId) {
        List <CompleteHorse> tempList = completeHorseRepo.findAllByLap_Id(competitionId);
        List <CompleteHorseDTO> completeHorseByFKList = tempList.stream()
                .map(e -> completeHorseToCompleteHorseDTO(e)).toList();
        return completeHorseByFKList;

    }

    public CompleteHorseDTO completeHorseToCompleteHorseDTO (CompleteHorse completeHorse) {
        return CompleteHorseDTO.builder()
                .id(completeHorse.getId())
                .nameOfCompleteHorse(completeHorse.getNameOfCompleteHorse())
                .build();
    }

    @Override
    public List<CompleteHorseDTOAnalys> getAllCompleteHorsesAnalysFromDB (Long competitionId) {
        List <CompleteHorse> tempList = completeHorseRepo.findAllByLap_Id(competitionId);
        List <CompleteHorseDTOAnalys> kukhorse = tempList.stream()
                .map(e -> completeHorseToCompleteHorseDTOAnalys(e)).toList();
        return kukhorse; //For forskning
    }

    public CompleteHorseDTOAnalys completeHorseToCompleteHorseDTOAnalys (CompleteHorse completeHorse) {
        return CompleteHorseDTOAnalys.builder()
                .id(completeHorse.getId())
                .nameOfCompleteHorse(completeHorse.getNameOfCompleteHorse())
                .fourStartsAnalys(completeHorse.getFourStarts().getAnalys())
                .build();
    }


    @Override
    public String saveDownCompleteHorsesToDB(CompleteHorse[] completeHorses) {
        List<CompleteHorse> completeHorsesList = Arrays.stream(completeHorses).toList();
        completeHorseRepo.saveAll(completeHorsesList);
        return "Allt gick bra med Hästar yo";
    }

    @Override
    public CompleteHorse[] getCompleteHorsesFromJsonFile() throws IOException{
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        File jsonFile = new File("C:\\Users\\TaraR\\IdeaProjects\\TravAnalysServer\\src\\main\\java\\com\\example\\travanalysserver\\data\\completeHorses.json");

        CompleteHorse[] completeHorsesFromJson = objectMapper.readValue(jsonFile, CompleteHorse[].class);
        return completeHorsesFromJson;
    }

}
