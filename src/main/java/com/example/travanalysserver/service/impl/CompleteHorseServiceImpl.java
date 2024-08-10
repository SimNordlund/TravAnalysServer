package com.example.travanalysserver.service.impl;

import com.example.travanalysserver.dto.completehorse.CompleteHorseDTO;
import com.example.travanalysserver.entity.CompleteHorse;
import com.example.travanalysserver.repository.CompleteHorseRepo;
import com.example.travanalysserver.service.interfaces.CompleteHorseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CompleteHorseServiceImpl implements CompleteHorseService {

    private CompleteHorseRepo completeHorseRepo;

    @Override
    public List<CompleteHorseDTO> findCompleteHorseByCompetitionId(Long competitionId) {
        List <CompleteHorse> tempList = completeHorseRepo.findAllByLap_Id(competitionId);
        List <CompleteHorseDTO> completeHorseByFKList = tempList.stream().map(e -> completeHorseToCompleteHorseDTO(e)).toList();
        return completeHorseByFKList;

    }

    public CompleteHorseDTO completeHorseToCompleteHorseDTO (CompleteHorse completeHorse) {
        return CompleteHorseDTO.builder()
                .id(completeHorse.getId())
                .nameOfCompleteHorse(completeHorse.getNameOfCompleteHorse())
                .build();
    }



}
