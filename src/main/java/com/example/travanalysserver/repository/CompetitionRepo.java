package com.example.travanalysserver.repository;

import com.example.travanalysserver.entity.Competition;
import com.example.travanalysserver.entity.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompetitionRepo extends JpaRepository <Competition, Long> {
    List<Competition> findAllByTrack_Id(Long trackId);

    void deleteAllByTrack(Track track);
}
