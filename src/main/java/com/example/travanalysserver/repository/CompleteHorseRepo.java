package com.example.travanalysserver.repository;

import com.example.travanalysserver.dto.skrallar.SkrallarHorseDto;
import com.example.travanalysserver.entity.CompleteHorse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
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
        and s.id = (  
            select max(s2.id)             
            from Starts s2
            where s2.completeHorse.id = h.id
              and s2.tips = (              
                  select max(s3.tips)
                  from Starts s3
                  where s3.completeHorse.id = h.id
              )
              and s2.analys = (            
                  select max(s4.analys)
                  from Starts s4
                  where s4.completeHorse.id = h.id
                    and s4.tips = (
                        select max(s5.tips) from Starts s5 where s5.completeHorse.id = h.id
                    )
              )
        )
      order by t.nameOfTrack, c.nameOfCompetition, l.nameOfLap, h.numberOfCompleteHorse
      """)
    List<SkrallarHorseDto> findByDateAndMinTips(@Param("date") LocalDate date,
                                                @Param("minTips") int minTips);
}

