package com.example.travanalysserver.service.impl;

import com.example.travanalysserver.dto.starts.EightStartsDTO;
import com.example.travanalysserver.entity.EightStarts;
import com.example.travanalysserver.repository.EightStartsRepo;
import com.example.travanalysserver.service.interfaces.EightStartsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EightStartsServiceImpl implements EightStartsService {

    private EightStartsRepo eightStartsRepo;

    @Override
    public EightStartsDTO findEightStartsSingleData (Long completeHorseId){
        EightStartsDTO eightStartsData = eightStartsToEightStartsDTO(eightStartsRepo.findByCompleteHorse_Id(completeHorseId));
        return eightStartsData;
    }

    public EightStartsDTO eightStartsToEightStartsDTO (EightStarts eightStarts){
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
                //.tips(eightStarts.getTips())
                .a1(eightStarts.getA1())
                .a2(eightStarts.getA2())
                .a3(eightStarts.getA3())
                .a4(eightStarts.getA4())
                .a5(eightStarts.getA5())
                .a6(eightStarts.getA6())
                .build();
    }

}
