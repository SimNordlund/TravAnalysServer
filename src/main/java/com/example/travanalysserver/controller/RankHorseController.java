package com.example.travanalysserver.controller;

import com.example.travanalysserver.entity.*;
import com.example.travanalysserver.entity.schedule.SyncMeta;
import com.example.travanalysserver.entitysec.RankHorseView;
import com.example.travanalysserver.entitysec.RoiView;
import com.example.travanalysserver.repository.SyncMetaRepo;
import com.example.travanalysserver.repository.TrackRepo;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ranked")
public class RankHorseController {

    private final RankHorseRepo  rankHorseRepo;
    private final RoiRepo        roiRepo;
    private final TrackRepo      trackRepo;
    private final SyncMetaRepo   syncMetaRepo;

    private static final Map<String,String> BANKOD_TO_TRACK = Map.ofEntries(
            Map.entry("Ar","Arvika"), Map.entry("Ax","Axevalla"),
            /* … rest of your map … */
            Map.entry("Ös","Östersund")
    );

    private static final DateTimeFormatter BASIC = DateTimeFormatter.BASIC_ISO_DATE;

    @GetMapping("/print")
    @Transactional
    public ResponseEntity<String> saveAllRanked() {
        // 1) lastRun
        LocalDateTime lastRun = syncMetaRepo.findById("ranked_horses")
                .map(SyncMeta::getLastRun)
                .orElse(LocalDateTime.of(1970,1,1,0,0));

        // 2) fetch delta
        List<RankHorseView> list = rankHorseRepo.findAllByUpdatedAtAfter(lastRun);
        if (list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("No new or changed rows since " + lastRun);
        }

        // 3) ROI
        List<RoiView> roiRows = roiRepo.findAllProjectedBy();
        Map<Long,RoiView> roiMap = roiRows.stream()
                .collect(Collectors.toMap(RoiView::getRankId, Function.identity()));

        // 4) preload existing Tracks
        Set<LocalDate> dates = list.stream()
                .map(v -> toLocalDate(v.getDateRankedHorse()))
                .collect(Collectors.toSet());
        Set<String> trackNames = list.stream()
                .map(v -> BANKOD_TO_TRACK.getOrDefault(v.getTrackRankedHorse(), v.getTrackRankedHorse()))
                .collect(Collectors.toSet());
        List<Track> existing = trackRepo.findAllByDateInAndNameOfTrackIn(dates, trackNames);
        Map<String,Track> trackMap = existing.stream()
                .collect(Collectors.toMap(
                        t -> t.getDate()+"|"+t.getNameOfTrack(),
                        Function.identity()
                ));

        // 5) seed existing Competitions
        Map<String,Competition> compMap = new HashMap<>();
        trackMap.forEach((trackKey, track) -> {
            track.getCompetitions().stream()
                    .filter(c -> "Vinnare".equals(c.getNameOfCompetition()))
                    .findFirst()
                    .ifPresent(c -> compMap.put(trackKey, c));
        });

        Map<String,Lap> lapMap = new HashMap<>();

        // 6) build graph
        for (RankHorseView v : list) {
            LocalDate date      = toLocalDate(v.getDateRankedHorse());
            String    trackName = BANKOD_TO_TRACK.getOrDefault(v.getTrackRankedHorse(), v.getTrackRankedHorse());
            String    trackKey  = date+"|"+trackName;

            Track track = trackMap.computeIfAbsent(trackKey, k -> {
                Track t = new Track();
                t.setDate(date);
                t.setNameOfTrack(trackName);
                return t;
            });

            Competition comp = compMap.computeIfAbsent(trackKey, k -> {
                Competition c = new Competition();
                c.setNameOfCompetition("Vinnare");
                c.setTrack(track);
                track.getCompetitions().add(c);
                return c;
            });

            String lapKey = trackKey+"|"+v.getLapRankedHorse();
            Lap lap = lapMap.computeIfAbsent(lapKey, k -> {
                Lap l = new Lap();
                l.setNameOfLap(v.getLapRankedHorse());
                l.setCompetition(comp);
                comp.getLaps().add(l);
                return l;
            });

            FourStarts fs = new FourStarts();
            fs.setAnalys    (toInt(v.getAnalysRankedHorse()));
            fs.setFart      (toInt(v.getTidRankedHorse()));
            fs.setStyrka    (toInt(v.getPrestationRankedHorse()));
            fs.setKlass     (toInt(v.getMotstandRankedHorse()));
            fs.setPrispengar(toInt(v.getPrispengarRankedHorse()));
            fs.setKusk      (rand100());
            fs.setSpar      (rand100());

            RoiView roi = roiMap.get(v.getId());
            if (roi != null) {
                fs.setRoiTotalt (roi.getRoiTotalt());
                fs.setRoiVinnare(roi.getRoiVinnare());
                fs.setRoiPlats  (roi.getRoiPlats());
                fs.setRoiTrio   (roi.getRoiTrio());
            }

            CompleteHorse horse = new CompleteHorse();
            horse.setNumberOfCompleteHorse(v.getNr());
            horse.setNameOfCompleteHorse(v.getNameRankedHorse());
            horse.setLap(lap);
            lap.getHorses().add(horse);

            horse.setFourStarts(fs);
            fs.setCompleteHorse(horse);
        }

        // 7) persist
        trackRepo.saveAll(trackMap.values());

        // 8) update lastRun
        syncMetaRepo.save(new SyncMeta("ranked_horses", LocalDateTime.now()));

        return new ResponseEntity<>(
                "Processed " + list.size() + " new/changed since " + lastRun,
                HttpStatus.CREATED
        );
    }

    private static LocalDate toLocalDate(Integer yyyymmdd) {
        if (yyyymmdd == null) return LocalDate.MIN;
        String s = String.format("%08d", yyyymmdd);
        return LocalDate.parse(s, BASIC);
    }

    private static int toInt(String s) {
        if (s == null) return 0;
        s = s.trim().replace("%","").replace(",","." );
        try {
            return (int) Math.round(Double.parseDouble(s));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private static int rand100() {
        return ThreadLocalRandom.current().nextInt(1,101);
    }
}
