package com.example.travanalysserver.repository;

import com.example.travanalysserver.entity.TwelveStarts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TwelveStartsRepo extends JpaRepository<TwelveStarts, Long> {
    Optional<TwelveStarts> findByCompleteHorse_Id(Long completeHorseId);
}
