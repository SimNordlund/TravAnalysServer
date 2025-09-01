package com.example.travanalysserver.controller;

import com.example.travanalysserver.dto.starts.EightStartsDTO;
import com.example.travanalysserver.dto.starts.FourStartsDTO;
import com.example.travanalysserver.repository.EightStartsRepo;
import com.example.travanalysserver.service.interfaces.EightStartsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/eightStarts")
public class EightStartsController {

    private final EightStartsService eightStartsService;

    @GetMapping("/findData")
    public EightStartsDTO getEightStartsData (@RequestParam Long completeHorseId){
        return eightStartsService.findEightStartsSingleData(completeHorseId);
    }

}
