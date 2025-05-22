package com.example.travanalysserver.repositorysec;

import com.example.travanalysserver.entitysec.RankHorse;
import com.example.travanalysserver.entitysec.RankHorseView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

public interface RankHorseRepo extends JpaRepository<RankHorse, Long> {
    List<RankHorseView> findAllProjectedBy();

    List<RankHorseView> findAllByUpdatedAtAfter(LocalDateTime cutoff);
}
