package com.example.travanalysserver.repository;

import com.example.travanalysserver.entity.CompleteHorse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompleteHorseRepo extends JpaRepository <CompleteHorse, Long> {
    List<CompleteHorse> findAllByLap_Id(Long lapId);
}
