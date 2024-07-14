package com.example.travanalysserver.controller;

import com.example.travanalysserver.dto.radarhorse.RadarHorseDTO;
import com.example.travanalysserver.entity.RadarHorse;
import com.example.travanalysserver.repository.RadarHorseRepo;
import com.example.travanalysserver.service.interfaces.RadarHorseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/radar") //Byt namn sen? XD
public class RadarHorseController {

    private final RadarHorseService radarHorseService;
    private final RadarHorseRepo radarHorseRepo;

    List <RadarHorse> radarList;

    @GetMapping("/find/{id}")
    public RadarHorseDTO getRadarHorseById(@PathVariable Long id){
        return radarHorseService.getRadarHorseById(id);
    }

    @PutMapping("/store/single")
    public List<RadarHorse> saveRadarHorseById(@RequestBody RadarHorse radarHorse) {
        radarList.add(radarHorse);
        return  radarList;
    }

}
