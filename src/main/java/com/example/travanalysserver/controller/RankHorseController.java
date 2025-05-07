package com.example.travanalysserver.controller;

import com.example.travanalysserver.entity.*;
import com.example.travanalysserver.entitysec.RankHorseView;
import com.example.travanalysserver.entitysec.RoiView;
import com.example.travanalysserver.repository.*;
import com.example.travanalysserver.repositorysec.RankHorseRepo;
import com.example.travanalysserver.repositorysec.RoiRepo;
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
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ranked")
public class RankHorseController {

    /* ───────────────────────────────── Repositories ──────────────────────*/
    private final RankHorseRepo rankHorseRepo;
    private final TrackRepo     trackRepo;
    private final RoiRepo       roiRepo;

    /* ──────────────── bankod  →  full track name map ─────────────────── */
    private static final Map<String, String> BANKOD_TO_TRACK = Map.ofEntries(
            Map.entry("Ar", "Arvika"),     Map.entry("Ax", "Axevalla"),
            Map.entry("B",  "Bergsåker"),  Map.entry("Bo", "Boden"),
            Map.entry("Bs", "Bollnäs"),    Map.entry("D",  "Dannero"),
            Map.entry("Dj", "Dala Järna"), Map.entry("E",  "Eskilstuna"),
            Map.entry("J",  "Jägersro"),   Map.entry("F",  "Färjestad"),
            Map.entry("G",  "Gävle"),      Map.entry("Gt", "Göteborg trav"),
            Map.entry("H",  "Hagmyren"),   Map.entry("Hd", "Halmstad"),
            Map.entry("Hg", "Hoting"),     Map.entry("Kh", "Karlshamn"),
            Map.entry("Kr", "Kalmar"),     Map.entry("L",  "Lindesberg"),
            Map.entry("Ly", "Lycksele"),   Map.entry("Mp", "Mantorp"),
            Map.entry("Ov", "Oviken"),     Map.entry("Ro", "Romme"),
            Map.entry("Rä", "Rättvik"),    Map.entry("S",  "Solvalla"),
            Map.entry("Sk", "Skellefteå"), Map.entry("Sä", "Solänget"),
            Map.entry("Ti", "Tingsryd"),   Map.entry("Tt", "Täby Trav"),
            Map.entry("U",  "Umåker"),     Map.entry("Vd", "Vemdalen"),
            Map.entry("Vg", "Vaggeryd"),   Map.entry("Vi", "Visby"),
            Map.entry("Å",  "Åby"),        Map.entry("Åm", "Åmål"),
            Map.entry("År", "Årjäng"),     Map.entry("Ö",  "Örebro"),
            Map.entry("Ös", "Östersund")
    );

    private static final DateTimeFormatter BASIC = DateTimeFormatter.BASIC_ISO_DATE;

    /* ─────────────────────────── Persist instead of print ─────────*/
    @GetMapping("/print")
    @Transactional
    public ResponseEntity<String> saveAllRanked() {

        List<RankHorseView> list    = rankHorseRepo.findAllProjectedBy();
        List<RoiView>       roiRows = roiRepo.findAllProjectedBy();

        System.out.println(roiRows);
        System.out.println("Ovan är roiRows jao");

        Map<Long, RoiView> roiMap = roiRows.stream()
                .collect(Collectors.toMap(RoiView::getRankId, Function.identity())); //Changed!

        System.out.println(roiMap);
        System.out.println("Ovan är roiMap jao");

        /* de‑dupe caches: Track ▸ Competition(v75) ▸ Lap ------------------- */
        Map<String, Track>       trackMap = new HashMap<>();
        Map<String, Competition> compMap  = new HashMap<>();
        Map<String, Lap>         lapMap   = new HashMap<>();

        list.forEach(v -> {
            /* ---- 1. Track ---- */
            LocalDate date      = toLocalDate(v.getDateRankedHorse());
            String    bankod    = v.getTrackRankedHorse();
            String    trackName = BANKOD_TO_TRACK.getOrDefault(bankod, bankod);
            String    trackKey  = date + "|" + trackName;

            Track track = trackMap.computeIfAbsent(trackKey, k -> {
                Track t = new Track();
                t.setDate(date);
                t.setNameOfTrack(trackName);
                return t;
            });

            /* ---- 2. Competition (always “v75”) ---- */
            String       compKey     = trackKey; /* only one per track */
            Competition  competition = compMap.computeIfAbsent(compKey, k -> {
                Competition c = new Competition();
                c.setNameOfCompetition("v75");
                c.setTrack(track);
                track.getCompetitions().add(c);
                return c;
            });

            /* ---- 3. Lap ---- */
            String lapKey = compKey + "|" + v.getLapRankedHorse();
            Lap    lap    = lapMap.computeIfAbsent(lapKey, k -> {
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

            RoiView roi = roiMap.get(v.getId());

            System.out.println(roi);
            System.out.println("Ovan är roi jao duvet");
            if (roi != null) {
                fs.setRoiTotalt  (roi.getRoiTotalt());
                fs.setRoiVinnare (roi.getRoiVinnare());
                fs.setRoiPlats   (roi.getRoiPlats());
                fs.setRoiTrio    (roi.getRoiTrio());
            }

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
