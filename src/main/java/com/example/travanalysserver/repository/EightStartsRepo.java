package com.example.travanalysserver.repository;

import com.example.travanalysserver.entity.EightStarts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EightStartsRepo extends JpaRepository <EightStarts, Long> {
    EightStarts findByCompleteHorse_Id(Long completeHorseId);
}
