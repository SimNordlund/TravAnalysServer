package com.example.travanalysserver.repository;

import com.example.travanalysserver.entity.Race;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RaceRepo extends JpaRepository <Race, Long> {
}
