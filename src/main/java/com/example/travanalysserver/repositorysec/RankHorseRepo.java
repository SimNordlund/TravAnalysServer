package com.example.travanalysserver.repositorysec;

import com.example.travanalysserver.entitysec.RankHorse;
import com.example.travanalysserver.entitysec.RankHorseView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Collection;

import java.time.LocalDateTime;
import java.util.List;

public interface RankHorseRepo extends JpaRepository<RankHorse, Long> {
    List<RankHorseView> findAllProjectedBy();
    List<RankHorseView> findAllByUpdatedAtAfter(LocalDateTime cutoff);

    List<RankHorseView> findAllByDateRankedHorseIn(Collection<Integer> dates);

    @Query("""
            select distinct r.dateRankedHorse
            from RankHorse r
            where r.updatedAt > :cutoff
            order by r.dateRankedHorse
            """)
    List<Integer> findDistinctDatesChangedByRankSince(@Param("cutoff") LocalDateTime cutoff);

    @Query("""
            select distinct r.dateRankedHorse
            from RankHorse r
            where exists (
                select 1
                from Roi roi
                where roi.rankId = r.id
                  and roi.updatedAt > :cutoff
            )
            order by r.dateRankedHorse
            """)
    List<Integer> findDistinctDatesChangedByRoiSince(@Param("cutoff") LocalDateTime cutoff);

    @Query("""
            select r
            from RankHorse r
            where r.dateRankedHorse = :dateRankedHorse
              and (
                  r.updatedAt > :cutoff
                  or exists (
                      select 1
                      from Roi roi
                      where roi.rankId = r.id
                        and roi.updatedAt > :cutoff
                  )
              )
            order by r.trackRankedHorse, r.lapRankedHorse, r.nr, r.id
            """)
    List<RankHorseView> findChangedForDateSince(
            @Param("dateRankedHorse") Integer dateRankedHorse,
            @Param("cutoff") LocalDateTime cutoff
    );
}
