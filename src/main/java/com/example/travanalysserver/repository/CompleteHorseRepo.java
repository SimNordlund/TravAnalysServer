package com.example.travanalysserver.repository;

import com.example.travanalysserver.dto.skrallar.SkrallarHorseDto;
import com.example.travanalysserver.entity.CompleteHorse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CompleteHorseRepo extends JpaRepository <CompleteHorse, Long> {
    List<CompleteHorse> findAllByLap_Id(Long lapId);

    @Query(value = """
    SELECT new com.example.travanalysserver.dto.skrallar.SkrallarHorseDto(
        ch.id,
        ch.nameOfCompleteHorse,
        fs.analys,
        fs.fart,
        fs.styrka,
        fs.klass,
        l.nameOfLap
    )
    FROM CompleteHorse ch
    JOIN ch.lap l
    JOIN l.competition c
    JOIN c.track t
    JOIN ch.fourStarts fs
    WHERE t.date = :date
    ORDER BY fs.analys DESC
    """)
    List<SkrallarHorseDto> findTop5ByDate(@Param("date") LocalDate date, Pageable pageable);

}
