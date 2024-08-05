package com.example.travanalysserver.controller;

import com.example.travanalysserver.dto.track.DateTrackDTO;
import com.example.travanalysserver.service.interfaces.TrackService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class TrackController {

    private final TrackService trackService;

    @GetMapping("/dates")
    public List<DateTrackDTO> getDatesFromDB(){
        List <DateTrackDTO> allDatesList = trackService.getAllDates();
        return allDatesList;
    }

  //  @GetMapping("/locations")
}
