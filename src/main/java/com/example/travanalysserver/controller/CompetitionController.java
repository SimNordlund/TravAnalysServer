package com.example.travanalysserver.controller;

import com.example.travanalysserver.dto.competition.CompetitionDTO;
import com.example.travanalysserver.service.interfaces.CompetitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/competition")
public class CompetitionController {

    private final CompetitionService competitionService;

    @GetMapping("/findByTrack")
    public List<CompetitionDTO> getAllCompetitionsByTrackFromDB (@RequestParam Long trackId){
        List <CompetitionDTO> allCompetitionsListByTrack = competitionService.findCompetitionByTrackId(trackId);
        return allCompetitionsListByTrack;
    }
}
