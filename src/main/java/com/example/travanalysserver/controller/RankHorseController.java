package com.example.travanalysserver.controller;

import com.example.travanalysserver.entity.*;
import com.example.travanalysserver.entity.schedule.SyncMeta;
import com.example.travanalysserver.entitysec.RankHorseView;
import com.example.travanalysserver.entitysec.RoiView;
import com.example.travanalysserver.repository.SyncMetaRepo;
import com.example.travanalysserver.repository.TrackRepo;
import com.example.travanalysserver.repositorysec.RankHorseRepo;
import com.example.travanalysserver.repositorysec.RoiRepo;
import com.example.travanalysserver.entity.Starts;

import com.example.travanalysserver.service.impl.PrimaryDbCleanupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ranked")
public class RankHorseController {

    private final RankHorseRepo  rankHorseRepo;
    private final RoiRepo        roiRepo;
    private final TrackRepo      trackRepo;
    private final SyncMetaRepo   syncMetaRepo;

    @PersistenceContext
    private EntityManager em;

    private final PrimaryDbCleanupService cleanup; //temp lösning

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
            Map.entry("Ös", "Östersund"),  Map.entry("Bj",  "Bjärke")
    );

    private static final DateTimeFormatter BASIC = DateTimeFormatter.BASIC_ISO_DATE;
    private static final DateTimeFormatter YYMMDD = DateTimeFormatter.ofPattern("yyMMdd");

    @GetMapping("/print/{dateInt}")
    @Transactional
    public ResponseEntity<String> saveRankedForDate(@PathVariable Integer dateInt) {
        LocalDate requestedDate;
        try {
            requestedDate = parseRequestedDate(dateInt);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Invalid date. Use 6 digits (yyMMdd) or 8 digits (yyyyMMdd).");
        }

        Set<Integer> dateCandidates = sourceDateCandidates(requestedDate);
        List<RankHorseView> workList = rankHorseRepo.findAllByDateRankedHorseIn(dateCandidates);

        if (workList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("No rows found for date " + requestedDate + " (" + dateCandidates + ")");
        }

        // Skriv över: ta bort befintligt för datumet i mål-DB först
        List<Track> existingForDate = trackRepo.findByDate(requestedDate);
        if (!existingForDate.isEmpty()) {
            trackRepo.deleteAll(existingForDate);
            em.flush();
            em.clear();
        }

        Set<Long> ids = workList.stream()
                .map(RankHorseView::getId)
                .collect(Collectors.toSet());

        Map<Long, RoiView> roiMap = ids.isEmpty()
                ? new HashMap<>()
                : roiRepo.findAllByRankIdIn(ids).stream()
                .collect(Collectors.toMap(RoiView::getRankId, Function.identity(), (a, b) -> b));

        // Samma bearbetning/sparlogik som i saveAllRanked, men för vald dag
        Map<String, List<RankHorseView>> byTrack = workList.stream()
                .collect(Collectors.groupingBy(v -> {
                    LocalDate d = toLocalDateFlexible(v.getDateRankedHorse());
                    String trackName = BANKOD_TO_TRACK
                            .getOrDefault(v.getTrackRankedHorse(), v.getTrackRankedHorse());
                    return d + "|" + trackName;
                }));

        int processed = 0;

        Set<LocalDate> dates = workList.stream()
                .map(v -> toLocalDateFlexible(v.getDateRankedHorse()))
                .collect(Collectors.toSet());

        Set<String> names = workList.stream()
                .map(v -> BANKOD_TO_TRACK.getOrDefault(v.getTrackRankedHorse(), v.getTrackRankedHorse()))
                .collect(Collectors.toSet());

        List<Track> existing = trackRepo.findAllByDateInAndNameOfTrackIn(dates, names);
        Map<String, Track> existingTrackMap = new HashMap<>();
        for (Track t : existing) {
            String k = t.getDate() + "|" + t.getNameOfTrack();
            existingTrackMap.put(k, t);
        }

        for (Map.Entry<String, List<RankHorseView>> entry : byTrack.entrySet()) {
            String key = entry.getKey();
            String[] parts = key.split("\\|", 2);
            LocalDate date = LocalDate.parse(parts[0]);
            String trackName = parts[1];

            Map<String,Competition>   compMap  = new HashMap<>();
            Map<String,Lap>           lapMap   = new HashMap<>();
            Map<String,CompleteHorse> horseMap = new HashMap<>();

            Track track = existingTrackMap.getOrDefault(key, null);
            if (track == null) {
                track = new Track();
                track.setDate(date);
                track.setNameOfTrack(trackName);
                existingTrackMap.put(key, track);
            }

            for (RankHorseView v : entry.getValue()) {
                String compName = normalizeCompetition(v.getCompetitionRankedHorse());
                String compKey  = key + "|" + compName;
                Track finalTrack = track;
                Competition comp = compMap.computeIfAbsent(compKey, k -> {
                    Competition c = new Competition();
                    c.setNameOfCompetition(compName);
                    c.setTrack(finalTrack);
                    finalTrack.getCompetitions().add(c);
                    return c;
                });

                String lKey = compKey + "|" + v.getLapRankedHorse();
                Lap lap = lapMap.computeIfAbsent(lKey, k -> {
                    Lap l = new Lap();
                    l.setNameOfLap(v.getLapRankedHorse());
                    l.setCompetition(comp);
                    comp.getLaps().add(l);
                    return l;
                });

                String hKey = lKey + "|" + v.getNr();
                CompleteHorse horse = horseMap.computeIfAbsent(hKey, k -> {
                    CompleteHorse h = new CompleteHorse();
                    h.setNumberOfCompleteHorse(v.getNr());
                    h.setLap(lap);
                    lap.getHorses().add(h);
                    return h;
                });

                horse.setNameOfCompleteHorse(v.getNameRankedHorse());

                String starter = normalizeStarter(v.getStarterRankedHorse());
                Starts s = getOrCreateStarts(horse, starter);

                s.setAnalys    (toInt(v.getAnalysRankedHorse()));
                s.setFart      (toInt(v.getTidRankedHorse()));
                s.setStyrka    (toInt(v.getPrestationRankedHorse()));
                s.setKlass     (toInt(v.getMotstandRankedHorse()));
                s.setPrispengar(toInt(v.getPrispengarRankedHorse()));
                s.setKusk      (toInt(v.getStallSkrikRankedHorse()));
                s.setPlacering (toInt(v.getPlaceringRankedHorse()));
                s.setForm      (toInt(v.getFormRankedHorse()));
                s.setA1        (toInt(v.getA1RankedHorse()));
                s.setA2        (toInt(v.getA2RankedHorse()));
                s.setA3        (toInt(v.getA3RankedHorse()));
                s.setA4        (toInt(v.getA4RankedHorse()));
                s.setA5        (toInt(v.getA5RankedHorse()));
                s.setA6        (toInt(v.getA6RankedHorse()));
                s.setTips      (toInt(v.getTipsRankedHorse()));

                RoiView roi = roiMap.get(v.getId());
                if (roi != null) {
                    s.setRoiTotalt (roi.getRoiTotalt());
                    s.setRoiSinceDayOne(roi.getRoiSinceDayOne());
                    s.setRoiVinnare(roi.getRoiVinnare());
                    s.setRoiPlats  (roi.getRoiPlats());
                    s.setRoiTrio   (roi.getRoiTrio());
                    s.setResultat  (roi.getResultat());
                }

                processed++;
            }

            trackRepo.save(existingTrackMap.get(key));
            em.flush();
            em.clear();
        }

        return new ResponseEntity<>(
                "Processed " + processed + " horses for date " + requestedDate,
                HttpStatus.CREATED
        );
    }

    @GetMapping("/print")
    @Transactional
    public ResponseEntity<String> saveAllRanked() {

        cleanup.truncateAllExceptEmailAndSyncMeta(); // trunkering sker i schedulern

        LocalDateTime lastRun = syncMetaRepo.findById("ranked_horses")
                .map(SyncMeta::getLastRun)
                .orElse(LocalDateTime.of(1970,1,1,0,0));

        List<RankHorseView> rankDelta =
                rankHorseRepo.findAllByUpdatedAtAfter(lastRun);
        List<RoiView> roiDelta =
                roiRepo.findAllByUpdatedAtAfter(lastRun);

        if (rankDelta.isEmpty() && roiDelta.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("No new or changed rows since " + lastRun);
        }

        Map<Long,RoiView> roiMap = roiDelta.stream()
                .collect(Collectors.toMap(RoiView::getRankId,
                        Function.identity(),
                        (a,b) -> b));

        roiMap.putAll(
                roiRepo.findAllProjectedBy().stream()
                        .filter(r -> !roiMap.containsKey(r.getRankId()))
                        .collect(Collectors.toMap(RoiView::getRankId, Function.identity()))
        );

        Set<Long> idsFromRoiDelta =
                roiDelta.stream().map(RoiView::getRankId).collect(Collectors.toSet());

        List<RankHorseView> workList = new ArrayList<>(rankDelta);
        if (!idsFromRoiDelta.isEmpty()) {
            workList.addAll(
                    rankHorseRepo.findAllProjectedBy().stream()
                            .filter(v -> idsFromRoiDelta.contains(v.getId()))
                            .toList());
        }

        // Gruppindela per (datum|bana) så vi kan spara och flush/clear per gru
        Map<String, List<RankHorseView>> byTrack = workList.stream()
                .collect(Collectors.groupingBy(v -> {
                    LocalDate d = toLocalDate(v.getDateRankedHorse());
                    String trackName = BANKOD_TO_TRACK
                            .getOrDefault(v.getTrackRankedHorse(), v.getTrackRankedHorse());
                    return d + "|" + trackName;
                }));

        int processed = 0;


        Set<LocalDate> dates = workList.stream()
                .map(v -> toLocalDate(v.getDateRankedHorse()))
                .collect(Collectors.toSet());

        Set<String> names = workList.stream()
                .map(v -> BANKOD_TO_TRACK.getOrDefault(v.getTrackRankedHorse(), v.getTrackRankedHorse()))
                .collect(Collectors.toSet());

        List<Track> existing = trackRepo.findAllByDateInAndNameOfTrackIn(dates, names);
        Map<String, Track> existingTrackMap = new HashMap<>();
        for (Track t : existing) {
            String k = t.getDate() + "|" + t.getNameOfTrack();
            existingTrackMap.put(k, t);
        }

        // Jobba grupp för grupp: håll liten graf i minnet och flush/clear direkt efter
        for (Map.Entry<String, List<RankHorseView>> entry : byTrack.entrySet()) {
            String key = entry.getKey(); //
            String[] parts = key.split("\\|", 2);
            LocalDate date = LocalDate.parse(parts[0]);
            String trackName = parts[1];

            // Lokala mappar för att inte växa heapen
            Map<String,Competition>   compMap  = new HashMap<>();
            Map<String,Lap>           lapMap   = new HashMap<>();
            Map<String,CompleteHorse> horseMap = new HashMap<>();

            // Återanvänd befintlig Track om den finns
            Track track = existingTrackMap.getOrDefault(key, null);
            if (track == null) {
                track = new Track();
                track.setDate(date);
                track.setNameOfTrack(trackName);
                existingTrackMap.put(key, track);
            }

            for (RankHorseView v : entry.getValue()) {
                String compName = normalizeCompetition(v.getCompetitionRankedHorse());
                String compKey  = key + "|" + compName;
                Track finalTrack = track;
                Competition comp = compMap.computeIfAbsent(compKey, k -> {
                    Competition c = new Competition();
                    c.setNameOfCompetition(compName);
                    c.setTrack(finalTrack);
                    finalTrack.getCompetitions().add(c);
                    return c;
                });

                String lKey = compKey + "|" + v.getLapRankedHorse();
                Lap lap = lapMap.computeIfAbsent(lKey, k -> {
                    Lap l = new Lap();
                    l.setNameOfLap(v.getLapRankedHorse());
                    l.setCompetition(comp);
                    comp.getLaps().add(l);
                    return l;
                });

                String hKey = lKey + "|" + v.getNr();
                CompleteHorse horse = horseMap.computeIfAbsent(hKey, k -> {
                    CompleteHorse h = new CompleteHorse();
                    h.setNumberOfCompleteHorse(v.getNr());
                    h.setLap(lap);
                    lap.getHorses().add(h);
                    return h;
                });

                horse.setNameOfCompleteHorse(v.getNameRankedHorse());

                String starter = normalizeStarter(v.getStarterRankedHorse());
                Starts s = getOrCreateStarts(horse, starter);

                s.setAnalys    (toInt(v.getAnalysRankedHorse()));
                s.setFart      (toInt(v.getTidRankedHorse()));
                s.setStyrka    (toInt(v.getPrestationRankedHorse()));
                s.setKlass     (toInt(v.getMotstandRankedHorse()));
                s.setPrispengar(toInt(v.getPrispengarRankedHorse()));
                s.setKusk      (toInt(v.getStallSkrikRankedHorse()));
                s.setPlacering (toInt(v.getPlaceringRankedHorse()));
                s.setForm      (toInt(v.getFormRankedHorse()));
                s.setA1        (toInt(v.getA1RankedHorse()));
                s.setA2        (toInt(v.getA2RankedHorse()));
                s.setA3        (toInt(v.getA3RankedHorse()));
                s.setA4        (toInt(v.getA4RankedHorse()));
                s.setA5        (toInt(v.getA5RankedHorse()));
                s.setA6        (toInt(v.getA6RankedHorse()));
                s.setTips      (toInt(v.getTipsRankedHorse()));

                RoiView roi = roiMap.get(v.getId());
                if (roi != null) {
                    s.setRoiTotalt (roi.getRoiTotalt());
                    s.setRoiSinceDayOne(roi.getRoiSinceDayOne());
                    s.setRoiVinnare(roi.getRoiVinnare());
                    s.setRoiPlats  (roi.getRoiPlats());
                    s.setRoiTrio   (roi.getRoiTrio());
                    s.setResultat  (roi.getResultat());
                }

                processed++;
            }

            // Spara EN bana och frigör minnet innan nästa
            trackRepo.save(existingTrackMap.get(key));
            em.flush();
            em.clear();
        }

        syncMetaRepo.save(new SyncMeta("ranked_horses", LocalDateTime.now()));

        return new ResponseEntity<>(
                "Processed " + processed + " horses since " + lastRun,
                HttpStatus.CREATED
        );
    }


    private static Starts getOrCreateStarts(CompleteHorse horse, String starter) {
        return horse.getStarts().stream()
                .filter(s -> starter.equals(s.getStarter()))
                .findFirst()
                .orElseGet(() -> {
                    Starts s = new Starts();
                    s.setStarter(starter);
                    s.setCompleteHorse(horse);
                    horse.getStarts().add(s);
                    return s;
                });
    }

    private static LocalDate toLocalDate(Integer yyyymmdd) {
        if (yyyymmdd == null) return LocalDate.MIN;
        return LocalDate.parse(String.format("%08d", yyyymmdd), BASIC);
    }

    private static LocalDate parseRequestedDate(Integer raw) {
        if (raw == null) throw new IllegalArgumentException("date is null");
        String s = String.valueOf(raw).trim();
        if (s.length() == 8) return LocalDate.parse(s, BASIC);
        if (s.length() == 6) return LocalDate.parse(s, YYMMDD);
        throw new IllegalArgumentException("Unsupported date format: " + s);
    }

    private static LocalDate toLocalDateFlexible(Integer rawDate) {
        if (rawDate == null) return LocalDate.MIN;
        String s = String.valueOf(rawDate).trim();
        if (s.length() == 8) return LocalDate.parse(s, BASIC);
        if (s.length() == 6) return LocalDate.parse(s, YYMMDD);
        return toLocalDate(rawDate);
    }

    private static Set<Integer> sourceDateCandidates(LocalDate d) {
        Set<Integer> out = new LinkedHashSet<>();
        out.add(Integer.parseInt(d.format(BASIC)));
        out.add(Integer.parseInt(d.format(YYMMDD)));
        return out;
    }

    private static int toInt(String s) {
        if (s == null) return 0;
        String t = s.trim().replace("%", "").replace(",", ".");
        try { return (int) Math.round(Double.parseDouble(t)); } catch (Exception e) { return 0; }
    }

    private static String normalizeCompetition(String s) {
        if (s == null) return "Vinnare"; //det ej problem jao
        String v = s.trim();
        return v.isEmpty() ? "Vinnare" : v;
    }

    private static String normalizeStarter(String s) {
        if (s == null) return "0";
        String t = s.trim();
        return t.isEmpty() ? "0" : t;
    }
}


