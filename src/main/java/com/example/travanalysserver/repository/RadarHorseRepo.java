package com.example.travanalysserver.repository;

import com.example.travanalysserver.entity.RadarHorse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RadarHorseRepo extends JpaRepository <RadarHorse, Long> {
}
