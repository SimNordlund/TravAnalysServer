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

        Track track = new Track();
        track.setNameOfTrack(request.getNameOfTrack());
        track.setDate(request.getDate());
        trackRepo.save(track);


        for (SendEverythingDTO.CompetitionDTO competitionDTO : request.getCompetitions()) {
            Competition competition = new Competition();
            competition.setNameOfCompetition(competitionDTO.getNameOfCompetition());
            competition.setTrack(track);
            track.getCompetitions().add(competition);
            competitionRepo.save(competition);


            for (SendEverythingDTO.CompetitionDTO.LapDTO lapDTO : competitionDTO.getLaps()) {
                Lap lap = new Lap();
                lap.setNameOfLap(lapDTO.getNameOfLap());
                lap.setCompetition(competition);
                competition.getLaps().add(lap);
                lapRepo.save(lap);


                for (SendEverythingDTO.CompetitionDTO.LapDTO.CompleteHorseDTO horseDTO : lapDTO.getHorses()) {
                    FourStarts fourStarts = new FourStarts();
                    fourStarts.setAnalys(horseDTO.getFourStarts().getAnalys());
                    fourStarts.setFart(horseDTO.getFourStarts().getFart());
                    fourStarts.setStyrka(horseDTO.getFourStarts().getStyrka());
                    fourStarts.setKlass(horseDTO.getFourStarts().getKlass());
                    fourStarts.setPrispengar(horseDTO.getFourStarts().getPrispengar());
                    fourStarts.setKusk(horseDTO.getFourStarts().getKusk());
                    fourStarts.setTips(horseDTO.getFourStarts().getTips());


                    CompleteHorse horse = new CompleteHorse();
                    horse.setNameOfCompleteHorse(horseDTO.getNameOfCompleteHorse());
                    horse.setNumberOfCompleteHorse(horseDTO.getNumberOfCompleteHorse());
                    horse.setLap(lap);
                    lap.getHorses().add(horse);
                    horse.setFourStarts(fourStarts);
                    fourStarts.setCompleteHorse(horse);

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


        track.setNameOfTrack(request.getNameOfTrack());
        track.setDate(request.getDate());


        competitionRepo.deleteAllByTrack(track);
        track.getCompetitions().clear();


        for (SendEverythingDTO.CompetitionDTO competitionDTO : request.getCompetitions()) {

            Competition competition = new Competition();
            competition.setNameOfCompetition(competitionDTO.getNameOfCompetition());
            competition.setTrack(track);
            track.getCompetitions().add(competition);
            competitionRepo.save(competition);

            for (SendEverythingDTO.CompetitionDTO.LapDTO lapDTO : competitionDTO.getLaps()) {

                Lap lap = new Lap();
                lap.setNameOfLap(lapDTO.getNameOfLap());
                lap.setCompetition(competition);
                competition.getLaps().add(lap);
                lapRepo.save(lap);

                for (SendEverythingDTO.CompetitionDTO.LapDTO.CompleteHorseDTO horseDTO : lapDTO.getHorses()) {

                    FourStarts fourStarts = new FourStarts();
                    fourStarts.setAnalys(horseDTO.getFourStarts().getAnalys());
                    fourStarts.setFart(horseDTO.getFourStarts().getFart());
                    fourStarts.setStyrka(horseDTO.getFourStarts().getStyrka());
                    fourStarts.setKlass(horseDTO.getFourStarts().getKlass());
                    fourStarts.setPrispengar(horseDTO.getFourStarts().getPrispengar());
                    fourStarts.setKusk(horseDTO.getFourStarts().getKusk());
                    fourStarts.setTips(horseDTO.getFourStarts().getTips());

                    CompleteHorse horse = new CompleteHorse();
                    horse.setNameOfCompleteHorse(horseDTO.getNameOfCompleteHorse());
                    horse.setLap(lap);
                    lap.getHorses().add(horse);

                    horse.setFourStarts(fourStarts);
                    fourStarts.setCompleteHorse(horse);

                    fourStartsRepo.save(fourStarts);
                    completeHorseRepo.save(horse);
                }
            }
        }


        return new ResponseEntity<>("Track overwritten successfully",
                HttpStatus.OK);
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
