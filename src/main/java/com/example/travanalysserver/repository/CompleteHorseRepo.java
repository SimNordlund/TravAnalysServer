package com.example.travanalysserver.repository;

import com.example.travanalysserver.dto.skrallar.SkrallarHorseDto;
import com.example.travanalysserver.entity.CompleteHorse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CompleteHorseRepo extends JpaRepository<CompleteHorse, Long> {

    List<CompleteHorse> findAllByLap_Id(Long lapId);

    @Query("""
      select new com.example.travanalysserver.dto.skrallar.SkrallarHorseDto(
          t.id, t.nameOfTrack,
          c.id, c.nameOfCompetition,
          l.id, l.nameOfLap,
          h.id, h.numberOfCompleteHorse, h.nameOfCompleteHorse,
          s.tips, s.analys, s.resultat, s.roiTotalt, s.roiVinnare, s.roiPlats
      )
      from Track t
        join t.competitions c
        join c.laps l
        join l.horses h
        join h.starts s
      where t.date = :date
        and s.tips >= :minTips
      order by t.nameOfTrack, c.nameOfCompetition, l.nameOfLap, h.numberOfCompleteHorse
      """)
    List<SkrallarHorseDto> findByDateAndMinTips(@Param("date") LocalDate date, @Param("minTips") int minTips);
}
