package com.example.travanalysserver.repository;

import com.example.travanalysserver.entity.Competition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompetitionRepo extends JpaRepository <Competition, Long> {
    List<Competition> findByTrack();
}
