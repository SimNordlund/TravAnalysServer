package com.example.travanalysserver.controller;

import com.example.travanalysserver.dto.competition.CompetitionDTO;
import com.example.travanalysserver.entity.Competition;
import com.example.travanalysserver.entity.Track;
import com.example.travanalysserver.repository.CompetitionRepo;
import com.example.travanalysserver.service.interfaces.CompetitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/competition")
public class CompetitionController {

    private final CompetitionService competitionService;
    private final CompetitionRepo competitionRepo;

    @GetMapping("/findByTrack")
    public List<CompetitionDTO> getAllCompetitionsByTrackFromDB (@RequestParam Long trackId){
        List <CompetitionDTO> allCompetitionsListByTrack = competitionService.findCompetitionByTrackId(trackId);
        return allCompetitionsListByTrack;
    }

    @PostMapping("/createCompetition")
    public ResponseEntity<String> addCompetition(@RequestBody Competition competition) {
        competitionRepo.save(competition);
        return new ResponseEntity<>("Tävlingstyp sparad", HttpStatus.CREATED);
    }
}
