package com.example.travanalysserver.service.interfaces;

import com.example.travanalysserver.dto.radarhorse.RadarHorseDTO;

import java.util.List;

public interface RadarHorseService {
    RadarHorseDTO getRadarHorseById(Long id);
    List<RadarHorseDTO> getAllRadarHorses();
}
