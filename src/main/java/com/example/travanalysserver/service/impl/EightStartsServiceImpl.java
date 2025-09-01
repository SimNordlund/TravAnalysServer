package com.example.travanalysserver.service.impl;

import com.example.travanalysserver.dto.starts.EightStartsDTO;
import com.example.travanalysserver.entity.EightStarts;
import com.example.travanalysserver.repository.EightStartsRepo;
import com.example.travanalysserver.service.interfaces.EightStartsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EightStartsServiceImpl implements EightStartsService {

    private final EightStartsRepo eightStartsRepo;

    @Override
    public EightStartsDTO findEightStartsSingleData(Long completeHorseId) {
        return eightStartsRepo.findByCompleteHorse_Id(completeHorseId)
                .map(this::eightStartsToEightStartsDTO)
                .orElseGet(this::emptyDto);
    }

    private EightStartsDTO eightStartsToEightStartsDTO(EightStarts eightStarts){
        if (eightStarts == null) return emptyDto();
        return EightStartsDTO.builder()
                .id(eightStarts.getId())
                .analys(eightStarts.getAnalys())
                .fart(eightStarts.getFart())
                .styrka(eightStarts.getStyrka())
                .klass(eightStarts.getKlass())
                .prispengar(eightStarts.getPrispengar())
                .kusk(eightStarts.getKusk())
                .placering(eightStarts.getPlacering())
                .form(eightStarts.getForm())
                .starter(eightStarts.getStarter())
                // .tips(eightStarts.getTips()) // saknas i entiteten
                .a1(eightStarts.getA1())
                .a2(eightStarts.getA2())
                .a3(eightStarts.getA3())
                .a4(eightStarts.getA4())
                .a5(eightStarts.getA5())
                .a6(eightStarts.getA6())
                .build();
    }

    private EightStartsDTO emptyDto() {
        return EightStartsDTO.builder()
                .analys(0).fart(0).styrka(0).klass(0)
                .prispengar(0).kusk(0).placering(0).form(0)
                .starter(0)
                .a1(0).a2(0).a3(0).a4(0).a5(0).a6(0)
                .build();
    }
}
