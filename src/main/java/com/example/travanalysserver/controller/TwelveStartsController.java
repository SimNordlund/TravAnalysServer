package com.example.travanalysserver.controller;

import com.example.travanalysserver.dto.starts.TwelveStartsDTO;
import com.example.travanalysserver.service.interfaces.TwelveStartsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/twelveStarts")
public class TwelveStartsController {
    final TwelveStartsService twelveStartsService;

    @GetMapping("/findData")
    public TwelveStartsDTO getTwelveStartsData (@RequestParam Long completeHorseId){
        return twelveStartsService.findTwelveStartsSingleData(completeHorseId);
    }
}
