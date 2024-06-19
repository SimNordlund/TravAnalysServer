package com.example.travanalysserver.repository;

import com.example.travanalysserver.entity.Lap;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LapRepo extends JpaRepository <Lap, Long> {
}
