package com.example.travanalysserver.repository;

import com.example.travanalysserver.entity.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TrackRepo extends JpaRepository <Track, Long> {
    List<Track> findByDate(LocalDate date);
}
