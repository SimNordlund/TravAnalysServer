package com.example.travanalysserver.repository;

import com.example.travanalysserver.entity.Track;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TrackRepo extends JpaRepository <Track, Long> {
    List<Track> findByDate(LocalDate date);
}
