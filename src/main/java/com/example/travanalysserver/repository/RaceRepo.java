package com.example.travanalysserver.repository;

import com.example.travanalysserver.entity.Track;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RaceRepo extends JpaRepository <Track, Long> {
}
