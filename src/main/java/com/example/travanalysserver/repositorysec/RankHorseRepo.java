package com.example.travanalysserver.repositorysec;  // Changed!: ensure this matches your controller import

import com.example.travanalysserver.entitysec.RankHorse;
import com.example.travanalysserver.entitysec.RankHorseView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface RankHorseRepo extends JpaRepository<RankHorse, Long> {
    List<RankHorseView> findAllProjectedBy(); // Changed!: projection method name
}
