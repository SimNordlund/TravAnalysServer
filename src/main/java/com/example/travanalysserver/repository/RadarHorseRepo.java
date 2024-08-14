package com.example.travanalysserver.repository;

import com.example.travanalysserver.entity.testing.RadarHorse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RadarHorseRepo extends JpaRepository <RadarHorse, Long> {
}
