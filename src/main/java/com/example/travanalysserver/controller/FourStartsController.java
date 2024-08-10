package com.example.travanalysserver.controller;

import com.example.travanalysserver.dto.starts.FourStartsDTO;
import com.example.travanalysserver.service.interfaces.FourStartsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fourStarts")
public class FourStartsController {

    private final FourStartsService fourStartsService;

    @GetMapping("/findData")
    public FourStartsDTO getFourStartsData (@RequestParam Long completeHorseId){
        return fourStartsService.findFourStartsSingleData(completeHorseId);
    }

}
