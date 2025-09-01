package com.example.travanalysserver.repository;

import com.example.travanalysserver.entity.FourStarts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FourStartsRepo extends JpaRepository <FourStarts, Long> {
    Optional<FourStarts> findByCompleteHorse_Id(Long completeHorseId);
}
