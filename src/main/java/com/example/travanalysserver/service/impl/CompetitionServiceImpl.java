package com.example.travanalysserver.service.impl;

import com.example.travanalysserver.dto.competition.CompetitionDTO;
import com.example.travanalysserver.entity.Competition;
import com.example.travanalysserver.repository.CompetitionRepo;
import com.example.travanalysserver.service.interfaces.CompetitionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CompetitionServiceImpl implements CompetitionService {

    CompetitionRepo competitionRepo;

    @Override
    public List<CompetitionDTO> findCompetitionByTrackId(Long trackId){
        List <Competition> tempList = competitionRepo.findByTrack();
        List <CompetitionDTO> competitionsByFKList = tempList.stream().map(e -> CompetitionToCompetitionDTO(e)).toList();
        return competitionsByFKList;
    }

    public CompetitionDTO CompetitionToCompetitionDTO (Competition competition){
        return CompetitionDTO.builder()
                .id(competition.getId())
                .nameOfCompetition(competition.getNameOfCompetition())
                .build();
    }
}
