package com.example.travanalysserver.controller;

import com.example.travanalysserver.dto.track.TrackDTO;
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

    private TrackService trackService;


    @GetMapping("/dates")
    public List<TrackDTO> getDatesFromDB(){
        List <TrackDTO> allDatesList = trackService.getAllDates();
        return allDatesList;
    }
}
