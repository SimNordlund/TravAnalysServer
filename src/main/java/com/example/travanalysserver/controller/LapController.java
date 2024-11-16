package com.example.travanalysserver.controller;

import com.example.travanalysserver.dto.lap.LapDTO;
import com.example.travanalysserver.entity.Competition;
import com.example.travanalysserver.entity.Lap;
import com.example.travanalysserver.repository.LapRepo;
import com.example.travanalysserver.service.interfaces.LapService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lap")
public class LapController {

    private final LapService lapService;
    private final LapRepo lapRepo;

    @GetMapping("/findByCompetition")
    public List<LapDTO> getAllLapsByCompetitionFromDB (@RequestParam Long competitionId) {
        List <LapDTO> allLapsListByCompetition = lapService.findAllByCompetition_Id(competitionId);
        return allLapsListByCompetition;

    }

    @PostMapping("/createLap")
    public ResponseEntity<String> addLap(@RequestBody Lap lap) {
        lapRepo.save(lap);
        return new ResponseEntity<>("Bana sparad", HttpStatus.CREATED);
    }

}
