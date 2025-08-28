package com.example.travanalysserver.repository;

import com.example.travanalysserver.entity.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface TrackRepo extends JpaRepository <Track, Long> {
    List<Track> findByDate(LocalDate date);

    List<Track> findAllByOrderByDateAsc();

    Optional<Track> findByNameOfTrack(String nameOfTrack);

    List<Track> findAllByDateInAndNameOfTrackIn(
            Collection<LocalDate> dates,
            Collection<String>    names
    );

    Optional<Track> findByNameOfTrackAndDate(String nameOfTrack, LocalDate date);
}
