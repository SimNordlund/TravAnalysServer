package com.example.travanalysserver.controller;

import com.example.travanalysserver.dto.track.DateTrackDTO;
import com.example.travanalysserver.dto.track.NameOfTrackDTO;
import com.example.travanalysserver.service.interfaces.TrackService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/track")
public class TrackController {

    private final TrackService trackService;

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
}
