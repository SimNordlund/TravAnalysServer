package com.example.travanalysserver.service.impl;

import com.example.travanalysserver.dto.starts.FourStartsDTO;
import com.example.travanalysserver.entity.FourStarts;
import com.example.travanalysserver.repository.FourStartsRepo;
import com.example.travanalysserver.service.interfaces.FourStartsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
                .spar(fourStarts.getSpar())
                .build();
    }

}
