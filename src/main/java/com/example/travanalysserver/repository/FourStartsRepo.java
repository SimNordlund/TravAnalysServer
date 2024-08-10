package com.example.travanalysserver.repository;

import com.example.travanalysserver.entity.FourStarts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FourStartsRepo extends JpaRepository <FourStarts, Long> {

    //Vilken använda???? Hämta som ett objekt eller lista
    FourStarts findByCompleteHorse_Id(Long completeHorseId);
  //  List <FourStarts> findAllByCompleteHorse_Id(Long completeHorseId);
}
