package com.example.travanalysserver.controller;

import com.example.travanalysserver.dto.competition.CompetitionDTO;
import com.example.travanalysserver.dto.completehorse.CompleteHorseDTO;
import com.example.travanalysserver.dto.completehorse.CompleteHorseDTOAnalys;
import com.example.travanalysserver.dto.skrallar.SkrallarHorseDto;
import com.example.travanalysserver.entity.Competition;
import com.example.travanalysserver.entity.CompleteHorse;
import com.example.travanalysserver.repository.CompleteHorseRepo;
import com.example.travanalysserver.service.interfaces.CompleteHorseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/completeHorse")
public class CompleteHorseController {

    private final CompleteHorseService completeHorseService;
    private final CompleteHorseRepo completeHorseRepo;

    @GetMapping("/findByLap")
    public List<CompleteHorseDTO> getAllCompleteHorsesFromDB (@RequestParam Long lapId) {
        List <CompleteHorseDTO> allCompleteHorseListByLap = completeHorseService.findCompleteHorseByCompetitionId(lapId);
        return allCompleteHorseListByLap;
    }

    @GetMapping("/findByLapAnalys")
    public List<CompleteHorseDTOAnalys> getAllCompleteHorsesAnalysFromDB (@RequestParam Long lapId) {
        List <CompleteHorseDTOAnalys> allCompleteHorseListAnalysByLap = completeHorseService.getAllCompleteHorsesAnalysFromDB(lapId);
        return allCompleteHorseListAnalysByLap;
    }

    @PostMapping("/createCompleteHorse")
    public ResponseEntity<String> addCompleteHorse(@RequestBody CompleteHorse completeHorse) {
        completeHorseRepo.save(completeHorse);
        return new ResponseEntity<>("Häst sparad", HttpStatus.CREATED);
    }

    @GetMapping("/getSkrallar")
    public ResponseEntity<List<SkrallarHorseDto>> getTop5HorsesByDate(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        Pageable topFive = PageRequest.of(0, 10);
        List<SkrallarHorseDto> topHorses = completeHorseRepo.findTop5ByDate(date, topFive);

        return new ResponseEntity<>(topHorses, HttpStatus.OK);
    }


}
