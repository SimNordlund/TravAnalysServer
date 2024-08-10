package com.example.travanalysserver.controller;

import com.example.travanalysserver.dto.competition.CompetitionDTO;
import com.example.travanalysserver.dto.completehorse.CompleteHorseDTO;
import com.example.travanalysserver.repository.CompleteHorseRepo;
import com.example.travanalysserver.service.interfaces.CompleteHorseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/completeHorse")
public class CompleteHorseController {

    private final CompleteHorseService completeHorseService;

    @GetMapping("/findByCompleteHorse")
    public List<CompleteHorseDTO> getAllCompleteHorsesFromDB (@RequestParam Long lapId) {
        List <CompleteHorseDTO> allCompleteHorseListByLap = completeHorseService.findCompleteHorseByCompetitionId(lapId);
        return allCompleteHorseListByLap;
    }

}
