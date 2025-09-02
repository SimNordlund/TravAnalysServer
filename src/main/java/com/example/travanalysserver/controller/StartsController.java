package com.example.travanalysserver.controller;

import com.example.travanalysserver.dto.starts.StartsDTO;
import com.example.travanalysserver.repository.StartsRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/starts")
public class StartsController {

    private final StartsRepo startsRepo;

    @GetMapping("/findData")
    public StartsDTO findData(@RequestParam Long completeHorseId, @RequestParam int starter) {
        return startsRepo.findByCompleteHorse_IdAndStarter(completeHorseId, starter)
                .map(s -> StartsDTO.builder()
                        .id(s.getId())
                        .analys(s.getAnalys()).fart(s.getFart()).styrka(s.getStyrka())
                        .klass(s.getKlass()).prispengar(s.getPrispengar()).kusk(s.getKusk())
                        .placering(s.getPlacering()).form(s.getForm()).starter(s.getStarter())
                        .a1(s.getA1()).a2(s.getA2()).a3(s.getA3()).a4(s.getA4()).a5(s.getA5()).a6(s.getA6())
                        .roiTotalt(s.getRoiTotalt()).roiVinnare(s.getRoiVinnare())
                        .roiPlats(s.getRoiPlats()).roiTrio(s.getRoiTrio()).resultat(s.getResultat())
                        .build())
                .orElseGet(() -> StartsDTO.builder()
                        .analys(0).fart(0).styrka(0).klass(0).prispengar(0).kusk(0)
                        .placering(0).form(0).starter(0).a1(0).a2(0).a3(0).a4(0).a5(0).a6(0)
                        .build());
    }

    @GetMapping("/available")
    public List<Integer> available(@RequestParam Long lapId) {
        return startsRepo.findAvailableStartersForLap(lapId).stream().sorted().toList();
    }
}
