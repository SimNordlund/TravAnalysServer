package com.example.travanalysserver.service.impl;

import com.example.travanalysserver.dto.starts.FourStartsDTO;
import com.example.travanalysserver.entity.FourStarts;
import com.example.travanalysserver.repository.FourStartsRepo;
import com.example.travanalysserver.service.interfaces.FourStartsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FourStartsServiceImpl implements FourStartsService {

    private final FourStartsRepo fourStartsRepo;

    @Override
    public FourStartsDTO findFourStartsSingleData(Long completeHorseId) {
        return fourStartsRepo.findByCompleteHorse_Id(completeHorseId)
                .map(this::fourStartsToFourStartsDTO)
                .orElseGet(this::emptyDto);
    }

    public FourStartsDTO fourStartsToFourStartsDTO(FourStarts fourStarts) {
        if (fourStarts == null) return emptyDto();
        return FourStartsDTO.builder()
                .id(fourStarts.getId())
                .analys(fourStarts.getAnalys())
                .fart(fourStarts.getFart())
                .styrka(fourStarts.getStyrka())
                .klass(fourStarts.getKlass())
                .prispengar(fourStarts.getPrispengar())
                .kusk(fourStarts.getKusk())
                .placering(fourStarts.getPlacering())
                .form(fourStarts.getForm())
                .tips(fourStarts.getTips())
                .starter(fourStarts.getStarter())
                .a1(fourStarts.getA1())
                .a2(fourStarts.getA2())
                .a3(fourStarts.getA3())
                .a4(fourStarts.getA4())
                .a5(fourStarts.getA5())
                .a6(fourStarts.getA6())
                .build();
    }

    private FourStartsDTO emptyDto() {
        return FourStartsDTO.builder()
                .analys(0).fart(0).styrka(0).klass(0)
                .prispengar(0).kusk(0).placering(0).form(0)
                .tips(0).starter(0)
                .a1(0).a2(0).a3(0).a4(0).a5(0).a6(0)
                .build();
    }

    @Override
    public String saveDownCompleteFourStartsToDB(FourStarts[] fourStarts) {
        List<FourStarts> fourStartsList = Arrays.stream(fourStarts).toList();
        fourStartsRepo.saveAll(fourStartsList);
        return "Allt gick bra med Hästar yo";
    }

    @Override
    public FourStarts[] getFourStartsFromJsonFile() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        File jsonFile = new File("C:\\Users\\TaraR\\IdeaProjects\\TravAnalysServer\\src\\main\\java\\com\\example\\travanalysserver\\data\\fourStarts.json");

        FourStarts[] fourStartsFromJson = objectMapper.readValue(jsonFile, FourStarts[].class);
        return fourStartsFromJson;
    }
}
