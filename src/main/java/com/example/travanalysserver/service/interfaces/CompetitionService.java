package com.example.travanalysserver.service.interfaces;

import com.example.travanalysserver.dto.competition.CompetitionDTO;
import com.example.travanalysserver.entity.Competition;
import com.example.travanalysserver.entity.Track;

import java.io.IOException;
import java.util.List;

public interface CompetitionService {
    List<CompetitionDTO> findCompetitionByTrackId(Long trackId);

    String saveDownCompetitionToDB(Competition competition);
    Competition getCompetitionFromJsonFile() throws IOException;

}
