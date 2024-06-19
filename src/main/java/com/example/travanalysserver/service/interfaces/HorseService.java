package com.example.travanalysserver.service.interfaces;

import com.example.travanalysserver.dto.horse.HorseDTO;
import com.example.travanalysserver.entity.Horse;

import java.util.Optional;

public interface HorseService {
    HorseDTO getHorseById(Long id);
    Horse HorseDtoToHorse(HorseDTO horseDTO);
    HorseDTO HorseToHorseDTO(Horse horse);

}
