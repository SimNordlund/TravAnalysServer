package com.example.travanalysserver.service.impl;

import com.example.travanalysserver.dto.horse.HorseDTO;
import com.example.travanalysserver.entity.Horse;
import com.example.travanalysserver.repository.HorseRepo;
import com.example.travanalysserver.service.interfaces.HorseService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class HorseServiceImpl implements HorseService {

    private HorseRepo horseRepo;

    @Override
    public HorseDTO getHorseById(Long id) {
        Horse tempHorse = horseRepo.findById(id).orElse(null);
        return HorseToHorseDTO(tempHorse);
    }

    @Override
    public Horse HorseDtoToHorse(HorseDTO horseDTO) {
        return Horse.builder()
                .id(horseDTO.getId())
                .name(horseDTO.getName())
                .build();
    }

    @Override
    public HorseDTO HorseToHorseDTO(Horse horse) {
        return HorseDTO.builder()
                .id(horse.getId())
                .name(horse.getName())
                .build();
    }
}
