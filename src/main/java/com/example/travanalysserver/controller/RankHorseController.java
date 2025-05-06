package com.example.travanalysserver.controller;

import com.example.travanalysserver.entity.*;
import com.example.travanalysserver.entitysec.RankHorseView;
import com.example.travanalysserver.repository.*;
import com.example.travanalysserver.repositorysec.RankHorseRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ranked")
public class RankHorseController {

    /* ───────────────────────────────── Repositories ──────────────────────*/
    private final RankHorseRepo rankHorseRepo;
    private final TrackRepo trackRepo;
    // competitionRepo, lapRepo, completeHorseRepo and fourStartsRepo
    // are no longer needed in this controller – Hibernate cascades do the work.

    /* ──────────────── bankod  →  full track name map ─────────────────── */             //Changed!
    private static final Map<String, String> BANKOD_TO_TRACK = Map.ofEntries(              //Changed!
            Map.entry("Ar", "Arvika"),     Map.entry("Ax", "Axevalla"),                   //Changed!
            Map.entry("B",  "Bergsåker"),  Map.entry("Bo", "Boden"),                     //Changed!
            Map.entry("Bs", "Bollnäs"),    Map.entry("D",  "Dannero"),                   //Changed!
            Map.entry("Dj", "Dala Järna"), Map.entry("E",  "Eskilstuna"),                //Changed!
            Map.entry("J",  "Jägersro"),   Map.entry("F",  "Färjestad"),                 //Changed!
            Map.entry("G",  "Gävle"),      Map.entry("Gt", "Göteborg trav"),             //Changed!
            Map.entry("H",  "Hagmyren"),   Map.entry("Hd", "Halmstad"),                  //Changed!
            Map.entry("Hg", "Hoting"),     Map.entry("Kh", "Karlshamn"),                 //Changed!
            Map.entry("Kr", "Kalmar"),     Map.entry("L",  "Lindesberg"),                //Changed!
            Map.entry("Ly", "Lycksele"),   Map.entry("Mp", "Mantorp"),                   //Changed!
            Map.entry("Ov", "Oviken"),     Map.entry("Ro", "Romme"),                     //Changed!
            Map.entry("Rä", "Rättvik"),    Map.entry("S",  "Solvalla"),                  //Changed!
            Map.entry("Sk", "Skellefteå"), Map.entry("Sä", "Solänget"),                  //Changed!
            Map.entry("Ti", "Tingsryd"),   Map.entry("Tt", "Täby Trav"),                 //Changed!
            Map.entry("U",  "Umåker"),     Map.entry("Vd", "Vemdalen"),                  //Changed!
            Map.entry("Vg", "Vaggeryd"),   Map.entry("Vi", "Visby"),                     //Changed!
            Map.entry("Å",  "Åby"),        Map.entry("Åm", "Åmål"),                      //Changed!
            Map.entry("År", "Årjäng"),     Map.entry("Ö",  "Örebro"),                    //Changed!
            Map.entry("Ös", "Östersund")                                                //Changed!
    );                                                                                   //Changed!

    private static final DateTimeFormatter BASIC = DateTimeFormatter.BASIC_ISO_DATE;

    /* ─────────────────────────── Persist instead of print ─────────*/
    @GetMapping("/print")
    @Transactional
    public ResponseEntity<String> saveAllRanked() {

        List<RankHorseView> list = rankHorseRepo.findAllProjectedBy();

        /* de‑dupe caches: Track ▸ Competition(v75) ▸ Lap ------------------- */
        Map<String, Track>       trackMap = new HashMap<>();
        Map<String, Competition> compMap  = new HashMap<>();
        Map<String, Lap>         lapMap   = new HashMap<>();

        list.forEach(v -> {
            /* ---- 1. Track ---- */
            LocalDate date      = toLocalDate(v.getDateRankedHorse());
            String bankod       = v.getTrackRankedHorse();                                   //Changed!
            String trackName    = BANKOD_TO_TRACK.getOrDefault(bankod, bankod);              //Changed!
            String trackKey     = date + "|" + trackName;                                    //Changed!

            Track track = trackMap.computeIfAbsent(trackKey, k -> {
                Track t = new Track();
                t.setDate(date);
                t.setNameOfTrack(trackName);                                                 //Changed!
                return t;
            });

            /* ---- 2. Competition (always “v75”) ---- */
            String compKey = trackKey; /* only one per track */
            Competition competition = compMap.computeIfAbsent(compKey, k -> {
                Competition c = new Competition();
                c.setNameOfCompetition("v75");
                c.setTrack(track);
                track.getCompetitions().add(c);
                return c;
            });

            /* ---- 3. Lap ---- */
            String lapKey = compKey + "|" + v.getLapRankedHorse();
            Lap lap = lapMap.computeIfAbsent(lapKey, k -> {
                Lap l = new Lap();
                l.setNameOfLap(v.getLapRankedHorse());
                l.setCompetition(competition);
                competition.getLaps().add(l);
                return l;
            });

            /* ---- 4. FourStarts ---- */
            FourStarts fs = new FourStarts();
            fs.setAnalys     (toInt(v.getAnalysRankedHorse()));
            fs.setFart       (toInt(v.getTidRankedHorse()));
            fs.setStyrka     (toInt(v.getPrestationRankedHorse()));
            fs.setKlass      (toInt(v.getMotstandRankedHorse()));
            fs.setPrispengar (toInt(v.getPrispengarRankedHorse()));
            fs.setKusk       (rand100());
            fs.setSpar       (rand100());

            /* ---- 5. CompleteHorse ---- */
            CompleteHorse horse = new CompleteHorse();
            horse.setNumberOfCompleteHorse(v.getNr());
            horse.setNameOfCompleteHorse(v.getNameRankedHorse());
            horse.setLap(lap);
            lap.getHorses().add(horse);

            horse.setFourStarts(fs);
            fs.setCompleteHorse(horse);
        });

        /* ---- One single flush does it all ------------------------------- */
        trackRepo.saveAll(trackMap.values());

        return new ResponseEntity<>(
                "Saved " + list.size() + " ranked horses to database",
                HttpStatus.CREATED);
    }

    /* ─────────────────────────────── helpers ──────────────────*/
    private static LocalDate toLocalDate(Integer yyyymmdd) {
        if (yyyymmdd == null) return LocalDate.MIN;
        String s = String.format("%08d", yyyymmdd);
        return LocalDate.parse(s, BASIC);
    }

    private static int toInt(String s) {
        if (s == null) return 0;
        s = s.trim()
                .replace("%", "")
                .replace(",", ".");
        try {
            double d = Double.parseDouble(s);
            return (int) Math.round(d);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private static int rand100() {
        return ThreadLocalRandom.current().nextInt(1, 101);
    }
}
