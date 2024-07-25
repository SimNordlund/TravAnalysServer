package com.example.travanalysserver.repository;

import com.example.travanalysserver.entity.RaceType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RaceTypeRepo extends JpaRepository <RaceType, Long> {
}
