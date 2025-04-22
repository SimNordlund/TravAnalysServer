package com.example.travanalysserver.controller;

import com.example.travanalysserver.dto.track.DateTrackDTO;
import com.example.travanalysserver.dto.track.NameOfTrackDTO;
import com.example.travanalysserver.entity.Competition;
import com.example.travanalysserver.entity.Track;
import com.example.travanalysserver.repository.TrackRepo;
import com.example.travanalysserver.service.interfaces.TrackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/track")
public class TrackController {

    private final TrackService trackService;
    private final TrackRepo trackRepo;

    @GetMapping("/all")
    public List<NameOfTrackDTO> getTracksToHandleTableData(){
        List <NameOfTrackDTO> allLocationsList = trackService.getAllNamesOfTracks();
        return allLocationsList;
    }

    @GetMapping("/dates")
    public List<DateTrackDTO> getDatesFromDB(){
        List <DateTrackDTO> allDatesList = trackService.getAllDates();
        return allDatesList;
    }

    @GetMapping("/locations")
    public List<NameOfTrackDTO> getLocationsFromDB(){
        List <NameOfTrackDTO> allLocationsList = trackService.getAllNamesOfTracks();
        return allLocationsList;
    }

    @GetMapping("/locations/byDate")
    public List<NameOfTrackDTO> getLocationsByDateFromDB(@RequestParam("date") String date){
        LocalDate localDate = LocalDate.parse(date);
        List <NameOfTrackDTO> allLocationsListByDate = trackService.getAllNamesOfTracksByDate(localDate);
        return allLocationsListByDate;
    }

    @PostMapping("/createTrack")
    public ResponseEntity<String> addTrack(@RequestBody Track track) {
        trackRepo.save(track);
        return new ResponseEntity<>("Bana sparad", HttpStatus.CREATED);
    }
}
