package com.example.travanalysserver.repository;

import com.example.travanalysserver.entity.CompleteHorse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompleteHorseRepo extends JpaRepository <CompleteHorse, Long> {
}
