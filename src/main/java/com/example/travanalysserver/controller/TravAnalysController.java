package com.example.travanalysserver.controller;

import com.example.travanalysserver.dto.travanalys.SendEverythingDTO;
import com.example.travanalysserver.entity.*;
import com.example.travanalysserver.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/travanalys")
public class TravAnalysController {

    private final TrackRepo trackRepo;
    private final CompetitionRepo competitionRepo;
    private final LapRepo lapRepo;
    private final CompleteHorseRepo completeHorseRepo;
    private final FourStartsRepo fourStartsRepo;

    @PostMapping("/sendEverything")
    public ResponseEntity<String> addEverything(@RequestBody SendEverythingDTO request) {
        // Save Track
        Track track = new Track();
        track.setNameOfTrack(request.getNameOfTrack());
        track.setDate(request.getDate());
        trackRepo.save(track);

        // Save Competitions
        for (SendEverythingDTO.CompetitionDTO competitionDTO : request.getCompetitions()) {
            Competition competition = new Competition();
            competition.setNameOfCompetition(competitionDTO.getNameOfCompetition());
            competition.setTrack(track);
            competitionRepo.save(competition);

            // Save Laps
            for (SendEverythingDTO.CompetitionDTO.LapDTO lapDTO : competitionDTO.getLaps()) {
                Lap lap = new Lap();
                lap.setNameOfLap(lapDTO.getNameOfLap());
                lap.setCompetition(competition);
                lapRepo.save(lap);

                // Save CompleteHorses
                for (SendEverythingDTO.CompetitionDTO.LapDTO.CompleteHorseDTO horseDTO : lapDTO.getHorses()) {
                    FourStarts fourStarts = new FourStarts();
                    fourStarts.setAnalys(horseDTO.getFourStarts().getAnalys());
                    fourStarts.setFart(horseDTO.getFourStarts().getFart());
                    fourStarts.setStyrka(horseDTO.getFourStarts().getStyrka());
                    fourStarts.setKlass(horseDTO.getFourStarts().getKlass());
                    fourStarts.setPrispengar(horseDTO.getFourStarts().getPrispengar());
                    fourStarts.setKusk(horseDTO.getFourStarts().getKusk());
                    fourStarts.setSpar(horseDTO.getFourStarts().getSpar());
                    fourStartsRepo.save(fourStarts);

                    CompleteHorse horse = new CompleteHorse();
                    horse.setNameOfCompleteHorse(horseDTO.getNameOfCompleteHorse());
                    horse.setLap(lap);
                    horse.setFourStarts(fourStarts);
                    completeHorseRepo.save(horse);
                }
            }
        }

        return new ResponseEntity<>("All data saved successfully!", HttpStatus.CREATED);
    }
}

