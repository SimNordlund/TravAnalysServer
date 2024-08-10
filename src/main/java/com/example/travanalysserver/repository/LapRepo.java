package com.example.travanalysserver.repository;

import com.example.travanalysserver.entity.Lap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LapRepo extends JpaRepository <Lap, Long> {
    List<Lap> findAllByCompetition_Id(Long competitionId);
}
