package com.example.travanalysserver.controller;

import com.example.travanalysserver.dto.lap.LapDTO;
import com.example.travanalysserver.entity.Lap;
import com.example.travanalysserver.service.interfaces.LapService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lap")
public class LapController {

    private final LapService lapService;

    @GetMapping("/findByCompetition")
    public List<LapDTO> getAllLapsByCompetitionFromDB (@RequestParam Long competitionId) {
        List <LapDTO> allLapsListByCompetition = lapService.findAllByCompetition_Id(competitionId);
        return allLapsListByCompetition;

    }

}
