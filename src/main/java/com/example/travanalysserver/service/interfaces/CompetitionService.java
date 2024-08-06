package com.example.travanalysserver.service.interfaces;

import com.example.travanalysserver.dto.competition.CompetitionDTO;

import java.util.List;

public interface CompetitionService {
    List<CompetitionDTO> findCompetitionByTrackId(Long trackId);
}
