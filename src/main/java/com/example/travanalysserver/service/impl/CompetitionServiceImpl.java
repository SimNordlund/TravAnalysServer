package com.example.travanalysserver.service.impl;

import com.example.travanalysserver.dto.competition.CompetitionDTO;
import com.example.travanalysserver.entity.Competition;
import com.example.travanalysserver.entity.Track;
import com.example.travanalysserver.repository.CompetitionRepo;
import com.example.travanalysserver.service.interfaces.CompetitionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class CompetitionServiceImpl implements CompetitionService {

    private CompetitionRepo competitionRepo;

    @Override
    public List<CompetitionDTO> findCompetitionByTrackId(Long trackId){
        List <Competition> tempList = competitionRepo.findAllByTrack_Id(trackId);
        List <CompetitionDTO> competitionsByFKList = tempList.stream().map(e -> CompetitionToCompetitionDTO(e)).toList();
        return competitionsByFKList;
    }

    public CompetitionDTO CompetitionToCompetitionDTO (Competition competition){
        return CompetitionDTO.builder()
                .id(competition.getId())
                .nameOfCompetition(competition.getNameOfCompetition())
                .build();
    }

    @Override
    public String saveDownCompetitionToDB(Competition competition) {
        competitionRepo.save(competition);
        return "Allt gick bra med Competition";
    }

    @Override
    public Competition getCompetitionFromJsonFile() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        File jsonFile = new File("C:\\Users\\TaraR\\IdeaProjects\\TravAnalysServer\\src\\main\\java\\com\\example\\travanalysserver\\data\\competition.json");

        Competition competitionFromJson = objectMapper.readValue(jsonFile, Competition.class);
        return competitionFromJson;
    }
}
