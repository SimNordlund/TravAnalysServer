package com.example.travanalysserver.controller;

import com.example.travanalysserver.dto.radarhorse.RadarHorseDTO;
import com.example.travanalysserver.entity.RadarHorse;
import com.example.travanalysserver.repository.RadarHorseRepo;
import com.example.travanalysserver.service.interfaces.RadarHorseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @GetMapping ("/find/all")
    public List <RadarHorseDTO> getAllRadarHorsesFromDB() {
        List <RadarHorseDTO> tempHolderList = radarHorseService.getAllRadarHorses();
        List <RadarHorseDTO> radarHorsesList = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            radarHorsesList.add(tempHolderList.get(i));
        }
        return radarHorsesList;
    }

    @GetMapping ("/find/all2")
    public List <RadarHorseDTO> getAllRadarHorsesFromDB2() {
        List <RadarHorseDTO> tempHolderList = radarHorseService.getAllRadarHorses();
        List <RadarHorseDTO> radarHorsesList = new ArrayList<>();
        for (int i = 10; i < 20; i++){
            radarHorsesList.add(tempHolderList.get(i));
        }
        return radarHorsesList;
    }

    @PutMapping("/store/single")
    public List<RadarHorse> saveRadarHorseById(@RequestBody RadarHorse radarHorse) {
        radarList.add(radarHorse);
        return  radarList;
    }

}
