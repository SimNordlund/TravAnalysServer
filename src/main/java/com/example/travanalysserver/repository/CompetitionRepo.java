package com.example.travanalysserver.repository;

import com.example.travanalysserver.entity.Competition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompetitionRepo extends JpaRepository <Competition, Long> {
}
