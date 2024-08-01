package com.example.travanalysserver.service.impl;

import com.example.travanalysserver.dto.radarhorse.RadarHorseDTO;
import com.example.travanalysserver.entity.testing.RadarHorse;
import com.example.travanalysserver.repository.RadarHorseRepo;
import com.example.travanalysserver.service.interfaces.RadarHorseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RadarHorseServiceImpl implements RadarHorseService {

    private RadarHorseRepo radarHorseRepo;

    @Override
    public RadarHorseDTO getRadarHorseById(Long id) {
        RadarHorse tempRadarHorse = radarHorseRepo.findById(id).orElse(null);
        if (tempRadarHorse == null) {
            System.out.println("Något blev fel i getRadarHorseByID");
        }
        return RadarHorseToRadarHorseDto(tempRadarHorse);
    }

    @Override
    public List <RadarHorseDTO> getAllRadarHorses() {
        List <RadarHorse> tempRadarHorseList = radarHorseRepo.findAll();
        List <RadarHorseDTO> radarHorsesDTOList = tempRadarHorseList.stream().map(e -> RadarHorseToRadarHorseDto(e)).toList();
        return radarHorsesDTOList;
    }


    public RadarHorse RadarHorseDtoToRadarHorse(RadarHorseDTO radarHorseDTO) {
        return RadarHorse.builder()
                .id(radarHorseDTO.getId())
                .name(radarHorseDTO.getName())
                .valueOne(radarHorseDTO.getValueOne())
                .valueTwo(radarHorseDTO.getValueTwo())
                .valueThree(radarHorseDTO.getValueThree())
                .valueFour(radarHorseDTO.getValueFour())
                .valueFive(radarHorseDTO.getValueFive())
                .build();
    }

    public RadarHorseDTO RadarHorseToRadarHorseDto(RadarHorse radarHorse) {
        return RadarHorseDTO.builder()
                .id(radarHorse.getId())
                .name(radarHorse.getName())
                .valueOne(radarHorse.getValueOne())
                .valueTwo(radarHorse.getValueTwo())
                .valueThree(radarHorse.getValueThree())
                .valueFour(radarHorse.getValueFour())
                .valueFive(radarHorse.getValueFive())
                .build();
    }

}
