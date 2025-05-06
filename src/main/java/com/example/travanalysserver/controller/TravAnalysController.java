package com.example.travanalysserver.controller;

import com.example.travanalysserver.dto.travanalys.SendEverythingDTO;
import com.example.travanalysserver.entity.*;
import com.example.travanalysserver.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
    @Transactional
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
            track.getCompetitions().add(competition);             //Changed!
            competitionRepo.save(competition);

            // Save Laps
            for (SendEverythingDTO.CompetitionDTO.LapDTO lapDTO : competitionDTO.getLaps()) {
                Lap lap = new Lap();
                lap.setNameOfLap(lapDTO.getNameOfLap());
                lap.setCompetition(competition);
                competition.getLaps().add(lap);                         //Changed!
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
                    // completeHorse reference set later

                    CompleteHorse horse = new CompleteHorse();
                    horse.setNameOfCompleteHorse(horseDTO.getNameOfCompleteHorse());
                    horse.setNumberOfCompleteHorse(horseDTO.getNumberOfCompleteHorse());
                    horse.setLap(lap);
                    lap.getHorses().add(horse);                             //Changed!
                    horse.setFourStarts(fourStarts);
                    fourStarts.setCompleteHorse(horse);                     //Changed!

                    fourStartsRepo.save(fourStarts);
                    completeHorseRepo.save(horse);
                }
            }
        }

        return new ResponseEntity<>("All data saved successfully!", HttpStatus.CREATED);
    }

    @PutMapping("/sendEverything/{trackId}")
    @Transactional
    public ResponseEntity<String> overwriteEverything(@PathVariable Long trackId, @RequestBody SendEverythingDTO request) {
        Optional<Track> trackOpt = trackRepo.findById(trackId);
        if (trackOpt.isEmpty()) {
            return new ResponseEntity<>("Track not found",
                    HttpStatus.NOT_FOUND);
        }
        Track track = trackOpt.get();

        /* 1. update root‑level fields -------------------------------- */
        track.setNameOfTrack(request.getNameOfTrack());
        track.setDate(request.getDate());

        /* 2. wipe old child rows ------------------------------------- */
        competitionRepo.deleteAllByTrack(track);
        track.getCompetitions().clear();             //Changed! keeps 1st‑level cache clean

        /* 3. rebuild new hierarchy ----------------------------------- */
        for (SendEverythingDTO.CompetitionDTO competitionDTO : request.getCompetitions()) {

            Competition competition = new Competition();                //Changed!
            competition.setNameOfCompetition(competitionDTO.getNameOfCompetition());
            competition.setTrack(track);                                //Changed!
            track.getCompetitions().add(competition);                   //Changed!
            competitionRepo.save(competition);                          //Changed!

            for (SendEverythingDTO.CompetitionDTO.LapDTO lapDTO : competitionDTO.getLaps()) {

                Lap lap = new Lap();                                     //Changed!
                lap.setNameOfLap(lapDTO.getNameOfLap());                //Changed!
                lap.setCompetition(competition);                         //Changed!
                competition.getLaps().add(lap);                         //Changed!
                lapRepo.save(lap);                                      //Changed!

                for (SendEverythingDTO.CompetitionDTO.LapDTO.CompleteHorseDTO horseDTO : lapDTO.getHorses()) {

                    FourStarts fourStarts = new FourStarts();            //Changed!
                    fourStarts.setAnalys(horseDTO.getFourStarts().getAnalys());
                    fourStarts.setFart(horseDTO.getFourStarts().getFart());
                    fourStarts.setStyrka(horseDTO.getFourStarts().getStyrka());
                    fourStarts.setKlass(horseDTO.getFourStarts().getKlass());
                    fourStarts.setPrispengar(horseDTO.getFourStarts().getPrispengar());
                    fourStarts.setKusk(horseDTO.getFourStarts().getKusk());
                    fourStarts.setSpar(horseDTO.getFourStarts().getSpar());

                    CompleteHorse horse = new CompleteHorse();             //Changed!
                    horse.setNameOfCompleteHorse(horseDTO.getNameOfCompleteHorse());
                    horse.setLap(lap);                                     //Changed!
                    lap.getHorses().add(horse);                           //Changed!

                    horse.setFourStarts(fourStarts);                      //Changed!
                    fourStarts.setCompleteHorse(horse);                   //Changed!

                    fourStartsRepo.save(fourStarts);                      //Changed!
                    completeHorseRepo.save(horse);                        //Changed!
                }
            }
        }

        /* 4. done ---------------------------------------------------- */
        return new ResponseEntity<>("Track overwritten successfully",   //Changed!
                HttpStatus.OK);                     //Changed!
    }

    @DeleteMapping("/deleteTrackById/{trackId}")
    @Transactional
    public ResponseEntity<String> deleteTrackById(@PathVariable Long trackId) {
        Optional<Track> trackOptional = trackRepo.findById(trackId);

        if (trackOptional.isEmpty()) {
            return new ResponseEntity<>("Track not found", HttpStatus.NOT_FOUND);
        }

        trackRepo.delete(trackOptional.get());

        return new ResponseEntity<>("Track and all connected entities deleted successfully", HttpStatus.OK);
    }

    @DeleteMapping("/deleteTrackByName")
    public ResponseEntity<String> deleteTrackByName(@RequestParam String nameOfTrack) {
        Optional<Track> trackOptional = trackRepo.findByNameOfTrack(nameOfTrack);

        if (trackOptional.isEmpty()) {
            return new ResponseEntity<>("Track not found", HttpStatus.NOT_FOUND);
        }

        trackRepo.delete(trackOptional.get());
        return new ResponseEntity<>("Track and all connected entities deleted successfully", HttpStatus.OK);
    }
}
