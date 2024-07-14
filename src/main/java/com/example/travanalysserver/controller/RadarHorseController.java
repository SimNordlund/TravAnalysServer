package com.example.travanalysserver.controller;

import com.example.travanalysserver.dto.radarhorse.RadarHorseDTO;
import com.example.travanalysserver.entity.RadarHorse;
import com.example.travanalysserver.service.interfaces.RadarHorseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/radar") //Byt namn sen? XD
public class RadarHorseController {

    private final RadarHorseService radarHorseService;

    @GetMapping("/find/{id}")
    public RadarHorseDTO getRadarHorseById(@PathVariable Long id){
        return radarHorseService.getRadarHorseById(id);
    }

 //   @PostMapping("/store/{id}")
 //   public RadarHorse saveRadarHorseById(@)

}
