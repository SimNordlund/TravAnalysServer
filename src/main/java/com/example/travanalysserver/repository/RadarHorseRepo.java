package com.example.travanalysserver.repository;

import com.example.travanalysserver.entity.testing.RadarHorse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RadarHorseRepo extends JpaRepository <RadarHorse, Long> {
}
