//Changed!
package com.example.travanalysserver.repository;

import com.example.travanalysserver.entity.Starts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StartsRepo extends JpaRepository<Starts, Long> {
    Optional<Starts> findByCompleteHorse_IdAndStarter(Long completeHorseId, int starter);

    @Query("""
            select distinct s.starter
            from Starts s
            where s.completeHorse.lap.id = :lapId
            """)
    List<Integer> findAvailableStartersForLap(Long lapId);
}
