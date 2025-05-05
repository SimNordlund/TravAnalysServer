package com.example.travanalysserver.controller;

import com.example.travanalysserver.entity.*;                                    //Changed!
import com.example.travanalysserver.entitysec.RankHorseView;
import com.example.travanalysserver.repository.*;
import com.example.travanalysserver.repositorysec.RankHorseRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;                                      //Changed!
import org.springframework.http.ResponseEntity;                                 //Changed!
import org.springframework.transaction.annotation.Transactional;                //Changed!
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;                                                     //Changed!
import java.time.format.DateTimeFormatter;                                      //Changed!
import java.util.*;                                                             //Changed!
import java.util.concurrent.ThreadLocalRandom;                                  //Changed!

@RestController
@RequiredArgsConstructor
@RequestMapping("/ranked")
public class RankHorseController {

    /* ───────────────────────────────── Repositories ───────────────────────── */
    private final RankHorseRepo rankHorseRepo;                                  //Unchanged
    private final TrackRepo trackRepo;                                          //Changed!
    private final CompetitionRepo competitionRepo;                              //Changed!
    private final LapRepo lapRepo;                                              //Changed!
    private final CompleteHorseRepo completeHorseRepo;                          //Changed!
    private final FourStartsRepo fourStartsRepo;                                //Changed!

    private static final DateTimeFormatter BASIC =                              //Changed!
            DateTimeFormatter.BASIC_ISO_DATE;                                   //Changed!

    /* ─────────────────────────── Persist instead of print ─────────────────── */
    @GetMapping("/print")                                                       //Unchanged URL – keeps callers working
    @Transactional                                                              //Changed!
    public ResponseEntity<String> saveAllRanked() {                             //Changed!
        List<RankHorseView> list = rankHorseRepo.findAllProjectedBy();          //Unchanged

        /* de‑dupe caches: Track ▸ Competition(v75) ▸ Lap ------------------- */
        Map<String, Track>        trackMap = new HashMap<>();                   //Changed!
        Map<String, Competition>  compMap  = new HashMap<>();                   //Changed!
        Map<String, Lap>          lapMap   = new HashMap<>();                   //Changed!

        list.forEach(v -> {                                                     //Changed!
            /* ---- 1. Track ---- */
            LocalDate date = toLocalDate(v.getDateRankedHorse());               //Changed!
            String trackKey = date + "|" + v.getTrackRankedHorse();             //Changed!

            Track track = trackMap.computeIfAbsent(trackKey, k -> {             //Changed!
                Track t = new Track();                                          //Changed!
                t.setDate(date);                                                //Changed!
                t.setNameOfTrack(v.getTrackRankedHorse());                      //Changed!
                trackRepo.save(t);                                              //Changed!
                return t;                                                       //Changed!
            });

            /* ---- 2. Competition (always “v75”) ---- */
            String compKey = trackKey; /* only one per track */                 //Changed!
            Competition competition = compMap.computeIfAbsent(compKey, k -> {   //Changed!
                Competition c = new Competition();                              //Changed!
                c.setNameOfCompetition("v75");                                  //Changed!
                c.setTrack(track);                                              //Changed!
                track.getCompetitions().add(c);                                 //Changed!
                competitionRepo.save(c);                                        //Changed!
                return c;                                                       //Changed!
            });

            /* ---- 3. Lap ---- */
            String lapKey = compKey + "|" + v.getLapRankedHorse();              //Changed!
            Lap lap = lapMap.computeIfAbsent(lapKey, k -> {                     //Changed!
                Lap l = new Lap();                                              //Changed!
                l.setNameOfLap(v.getLapRankedHorse());                          //Changed!
                l.setCompetition(competition);                                  //Changed!
                competition.getLaps().add(l);                                   //Changed!
                lapRepo.save(l);                                                //Changed!
                return l;                                                       //Changed!
            });

            /* ---- 4. FourStarts ---- */
            FourStarts fs = new FourStarts();                                   //Changed!
            fs.setAnalys  (toInt(v.getTidRankedHorse()));       // procenttid    //Changed!
            fs.setFart    (toInt(v.getMotstandRankedHorse()));  // procentmotst  //Changed!
            fs.setStyrka  (toInt(v.getAnalysRankedHorse()));    // procentanalys //Changed!
            fs.setKlass   (toInt(v.getPrestationRankedHorse())); // proc prestation //Changed!
            fs.setPrispengar(rand100());                                        // filler //Changed!
            fs.setKusk     (rand100());                                         // filler //Changed!
            fs.setSpar     (rand100());                                         // filler //Changed!

            /* ---- 5. CompleteHorse ---- */
            CompleteHorse horse = new CompleteHorse();                          //Changed!
            horse.setNameOfCompleteHorse(v.getNr() + " " + v.getNameRankedHorse()); //Changed!
            horse.setLap(lap);                                                  //Changed!
            lap.getHorses().add(horse);                                         //Changed!

            horse.setFourStarts(fs);                                            //Changed!
            fs.setCompleteHorse(horse);                                         //Changed!

            fourStartsRepo.save(fs);                                            //Changed!
            completeHorseRepo.save(horse);                                      //Changed!
        });

        return new ResponseEntity<>(                                            //Changed!
                "Saved " + list.size() + " ranked horses to database",          //Changed!
                HttpStatus.CREATED);                                            //Changed!
    }

    /* ─────────────────────────────── helpers ─────────────────────────────── */
    private static LocalDate toLocalDate(Integer yyyymmdd) {                    //Changed!
        if (yyyymmdd == null) return LocalDate.MIN;                             //Changed!
        String s = String.format("%08d", yyyymmdd);                             //Changed!
        return LocalDate.parse(s, BASIC);                                       //Changed!
    }                                                                           //Changed!

    private static int toInt(String s) {                                        //Changed!
        if (s == null) return 0;                                                //Changed!
        s = s.trim().replace("%", "")                                           // remove % //Changed!
                .replace(",", ".");                                          // Swedish → . //Changed!
        try {                                                                   //Changed!
            double d = Double.parseDouble(s);                                   //Changed!
            return (int) Math.round(d);                                         //Changed!
        } catch (NumberFormatException e) {                                     //Changed!
            return 0;                                                           //Changed!
        }                                                                       //Changed!
    }                                                                           //Changed!

    private static int rand100() {                                              //Changed!
        return ThreadLocalRandom.current().nextInt(1, 101);                     //Changed!
    }                                                                           //Changed!
}
