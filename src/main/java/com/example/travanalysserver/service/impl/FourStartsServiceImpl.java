package com.example.travanalysserver.service.impl;

import com.example.travanalysserver.dto.starts.FourStartsDTO;
import com.example.travanalysserver.entity.FourStarts;
import com.example.travanalysserver.repository.FourStartsRepo;
import com.example.travanalysserver.service.interfaces.FourStartsService;
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
public class FourStartsServiceImpl implements FourStartsService {

    private FourStartsRepo fourStartsRepo;

    @Override
    public FourStartsDTO findFourStartsSingleData (Long completeHorseId){
        FourStartsDTO fourStartsData = fourStartsToFourStartsDTO(fourStartsRepo.findByCompleteHorse_Id(completeHorseId));
        return fourStartsData;
    }

    public FourStartsDTO fourStartsToFourStartsDTO (FourStarts fourStarts){
        return FourStartsDTO.builder()
                .id(fourStarts.getId())
                .analys(fourStarts.getAnalys())
                .fart(fourStarts.getFart())
                .styrka(fourStarts.getStyrka())
                .klass(fourStarts.getKlass())
                .prispengar(fourStarts.getPrispengar())
                .kusk(fourStarts.getKusk())
                .tips(fourStarts.getTips())
                .build();
    }


    @Override
    public String saveDownCompleteFourStartsToDB(FourStarts[] fourStarts) {
        List<FourStarts> fourStartsList = Arrays.stream(fourStarts).toList();
        fourStartsRepo.saveAll(fourStartsList);
        return "Allt gick bra med Hästar yo";
    }

    @Override
    public FourStarts[] getFourStartsFromJsonFile() throws IOException{
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        File jsonFile = new File("C:\\Users\\TaraR\\IdeaProjects\\TravAnalysServer\\src\\main\\java\\com\\example\\travanalysserver\\data\\fourStarts.json");

        FourStarts[] fourStartsFromJson = objectMapper.readValue(jsonFile, FourStarts[].class);
        return fourStartsFromJson;
    }
}
