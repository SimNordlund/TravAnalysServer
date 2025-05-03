package com.example.travanalysserver.controller;


import com.example.travanalysserver.entitysec.RankHorseView;
import com.example.travanalysserver.repository.*;
import com.example.travanalysserver.repositorysec.RankHorseRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ranked")
public class RankHorseController {

    private final RankHorseRepo rankHorseRepo;

    private final TrackRepo trackRepo;
    private final CompetitionRepo competitionRepo;
    private final LapRepo lapRepo;
    private final CompleteHorseRepo completeHorseRepo;
    private final FourStartsRepo fourStartsRepo;

    @GetMapping("/print")
    public String printAllRanked() {
        List<RankHorseView> list = rankHorseRepo.findAllProjectedBy();
        list.forEach(h -> System.out.println(
                "RankHorse[id="    + h.getId()                +
                        ", date="          + h.getDateRankedHorse()  +
                        ", track="         + h.getTrackRankedHorse() +
                        ", competition="   + h.getCompetitionRankedHorse() +
                        ", lap="           + h.getLapRankedHorse()   +
                        ", name="          + h.getNameRankedHorse()  +
                        ", analys="        + h.getAnalysRankedHorse()+
                        ", time="          + h.getTidRankedHorse()   +
                        ", pct="           + h.getPrestationRankedHorse() +
                        ", odds="          + h.getMotstandRankedHorse() +
                        "]"
        ));
        return "Printed " + list.size() + " ranked horses to console";
    }


}
