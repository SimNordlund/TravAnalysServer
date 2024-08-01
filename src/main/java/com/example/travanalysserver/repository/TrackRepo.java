package com.example.travanalysserver.repository;

import com.example.travanalysserver.entity.Track;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackRepo extends JpaRepository <Track, Long> {
}
