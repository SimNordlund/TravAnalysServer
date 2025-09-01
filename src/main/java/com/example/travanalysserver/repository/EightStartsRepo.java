package com.example.travanalysserver.repository;

import com.example.travanalysserver.entity.EightStarts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EightStartsRepo extends JpaRepository<EightStarts, Long> {
    Optional<EightStarts> findByCompleteHorse_Id(Long completeHorseId);
}
