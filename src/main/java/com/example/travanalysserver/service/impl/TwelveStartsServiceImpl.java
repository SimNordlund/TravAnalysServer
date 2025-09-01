package com.example.travanalysserver.service.impl;

import com.example.travanalysserver.dto.starts.TwelveStartsDTO;
import com.example.travanalysserver.entity.TwelveStarts;
import com.example.travanalysserver.repository.TwelveStartsRepo;
import com.example.travanalysserver.service.interfaces.TwelveStartsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TwelveStartsServiceImpl implements TwelveStartsService {

    private final TwelveStartsRepo twelveStartsRepo;

    @Override
    public TwelveStartsDTO findTwelveStartsSingleData(Long completeHorseId) {
        return twelveStartsRepo.findByCompleteHorse_Id(completeHorseId)
                .map(this::twelveStartsToTwelveStartsDTO)
                .orElseGet(this::emptyDto);
    }

    private TwelveStartsDTO twelveStartsToTwelveStartsDTO(TwelveStarts twelveStarts){
        if (twelveStarts == null) return emptyDto();
        return TwelveStartsDTO.builder()
                .id(twelveStarts.getId())
                .analys(twelveStarts.getAnalys())
                .fart(twelveStarts.getFart())

                .styrka(twelveStarts.getStyrka())
                .klass(twelveStarts.getKlass())
                .prispengar(twelveStarts.getPrispengar())
                .kusk(twelveStarts.getKusk())
                .placering(twelveStarts.getPlacering())
                .form(twelveStarts.getForm())
                .starter(twelveStarts.getStarter())
                // .tips(twelve.getTips())
                .a1(twelveStarts.getA1())
                .a2(twelveStarts.getA2())
                .a3(twelveStarts.getA3())
                .a4(twelveStarts.getA4())
                .a5(twelveStarts.getA5())
                .a6(twelveStarts.getA6())
                .build();
    }

    private TwelveStartsDTO emptyDto() {
        return TwelveStartsDTO.builder()
                .analys(0).fart(0).styrka(0).klass(0)
                .prispengar(0).kusk(0).placering(0).form(0)
                .starter(0)
                .a1(0).a2(0).a3(0).a4(0).a5(0).a6(0)
                .build();
    }

}
