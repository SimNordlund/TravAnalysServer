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
import org.springframework.web.bind.annotation.PathVariable; //Changed!
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
    private static final DateTimeFormatter YYMMDD = DateTimeFormatter.ofPattern("yyMMdd"); //Changed!

    @GetMapping("/print/{dateInt}") //Changed!
    @Transactional
    public ResponseEntity<String> saveRankedForDate(@PathVariable Integer dateInt) { //Changed!
        LocalDate requestedDate; //Changed!
        try { //Changed!
            requestedDate = parseRequestedDate(dateInt); //Changed!
        } catch (Exception e) { //Changed!
            return ResponseEntity.badRequest() //Changed!
                    .body("Invalid date. Use 6 digits (yyMMdd) or 8 digits (yyyyMMdd)."); //Changed!
        } //Changed!

        Set<Integer> dateCandidates = sourceDateCandidates(requestedDate); //Changed!
        List<RankHorseView> workList = rankHorseRepo.findAllByDateRankedHorseIn(dateCandidates); //Changed!

        if (workList.isEmpty()) { //Changed!
            return ResponseEntity.status(HttpStatus.NO_CONTENT) //Changed!
                    .body("No rows found for date " + requestedDate + " (" + dateCandidates + ")"); //Changed!
        } //Changed!

        // Skriv över: ta bort befintligt för datumet i mål-DB först //Changed!
        List<Track> existingForDate = trackRepo.findByDate(requestedDate); //Changed!
        if (!existingForDate.isEmpty()) { //Changed!
            trackRepo.deleteAll(existingForDate); //Changed!
            em.flush(); //Changed!
            em.clear(); //Changed!
        } //Changed!

        Set<Long> ids = workList.stream()
                .map(RankHorseView::getId)
                .collect(Collectors.toSet()); //Changed!

        Map<Long, RoiView> roiMap = ids.isEmpty() //Changed!
                ? new HashMap<>() //Changed!
                : roiRepo.findAllByRankIdIn(ids).stream() //Changed!
                .collect(Collectors.toMap(RoiView::getRankId, Function.identity(), (a, b) -> b)); //Changed!

        // Samma bearbetning/sparlogik som i saveAllRanked, men för vald dag //Changed!
        Map<String, List<RankHorseView>> byTrack = workList.stream() //Changed!
                .collect(Collectors.groupingBy(v -> { //Changed!
                    LocalDate d = toLocalDateFlexible(v.getDateRankedHorse()); //Changed!
                    String trackName = BANKOD_TO_TRACK //Changed!
                            .getOrDefault(v.getTrackRankedHorse(), v.getTrackRankedHorse()); //Changed!
                    return d + "|" + trackName; //Changed!
                })); //Changed!

        int processed = 0; //Changed!

        Set<LocalDate> dates = workList.stream() //Changed!
                .map(v -> toLocalDateFlexible(v.getDateRankedHorse())) //Changed!
                .collect(Collectors.toSet()); //Changed!

        Set<String> names = workList.stream() //Changed!
                .map(v -> BANKOD_TO_TRACK.getOrDefault(v.getTrackRankedHorse(), v.getTrackRankedHorse())) //Changed!
                .collect(Collectors.toSet()); //Changed!

        List<Track> existing = trackRepo.findAllByDateInAndNameOfTrackIn(dates, names); //Changed!
        Map<String, Track> existingTrackMap = new HashMap<>(); //Changed!
        for (Track t : existing) { //Changed!
            String k = t.getDate() + "|" + t.getNameOfTrack(); //Changed!
            existingTrackMap.put(k, t); //Changed!
        } //Changed!

        for (Map.Entry<String, List<RankHorseView>> entry : byTrack.entrySet()) { //Changed!
            String key = entry.getKey(); //Changed!
            String[] parts = key.split("\\|", 2); //Changed!
            LocalDate date = LocalDate.parse(parts[0]); //Changed!
            String trackName = parts[1]; //Changed!

            Map<String,Competition>   compMap  = new HashMap<>(); //Changed!
            Map<String,Lap>           lapMap   = new HashMap<>(); //Changed!
            Map<String,CompleteHorse> horseMap = new HashMap<>(); //Changed!

            Track track = existingTrackMap.getOrDefault(key, null); //Changed!
            if (track == null) { //Changed!
                track = new Track(); //Changed!
                track.setDate(date); //Changed!
                track.setNameOfTrack(trackName); //Changed!
                existingTrackMap.put(key, track); //Changed!
            } //Changed!

            for (RankHorseView v : entry.getValue()) { //Changed!
                String compName = normalizeCompetition(v.getCompetitionRankedHorse()); //Changed!
                String compKey  = key + "|" + compName; //Changed!
                Track finalTrack = track; //Changed!
                Competition comp = compMap.computeIfAbsent(compKey, k -> { //Changed!
                    Competition c = new Competition(); //Changed!
                    c.setNameOfCompetition(compName); //Changed!
                    c.setTrack(finalTrack); //Changed!
                    finalTrack.getCompetitions().add(c); //Changed!
                    return c; //Changed!
                }); //Changed!

                String lKey = compKey + "|" + v.getLapRankedHorse(); //Changed!
                Lap lap = lapMap.computeIfAbsent(lKey, k -> { //Changed!
                    Lap l = new Lap(); //Changed!
                    l.setNameOfLap(v.getLapRankedHorse()); //Changed!
                    l.setCompetition(comp); //Changed!
                    comp.getLaps().add(l); //Changed!
                    return l; //Changed!
                }); //Changed!

                String hKey = lKey + "|" + v.getNr(); //Changed!
                CompleteHorse horse = horseMap.computeIfAbsent(hKey, k -> { //Changed!
                    CompleteHorse h = new CompleteHorse(); //Changed!
                    h.setNumberOfCompleteHorse(v.getNr()); //Changed!
                    h.setLap(lap); //Changed!
                    lap.getHorses().add(h); //Changed!
                    return h; //Changed!
                }); //Changed!

                horse.setNameOfCompleteHorse(v.getNameRankedHorse()); //Changed!

                String starter = normalizeStarter(v.getStarterRankedHorse()); //Changed!
                Starts s = getOrCreateStarts(horse, starter); //Changed!

                s.setAnalys    (toInt(v.getAnalysRankedHorse())); //Changed!
                s.setFart      (toInt(v.getTidRankedHorse())); //Changed!
                s.setStyrka    (toInt(v.getPrestationRankedHorse())); //Changed!
                s.setKlass     (toInt(v.getMotstandRankedHorse())); //Changed!
                s.setPrispengar(toInt(v.getPrispengarRankedHorse())); //Changed!
                s.setKusk      (toInt(v.getStallSkrikRankedHorse())); //Changed!
                s.setPlacering (toInt(v.getPlaceringRankedHorse())); //Changed!
                s.setForm      (toInt(v.getFormRankedHorse())); //Changed!
                s.setA1        (toInt(v.getA1RankedHorse())); //Changed!
                s.setA2        (toInt(v.getA2RankedHorse())); //Changed!
                s.setA3        (toInt(v.getA3RankedHorse())); //Changed!
                s.setA4        (toInt(v.getA4RankedHorse())); //Changed!
                s.setA5        (toInt(v.getA5RankedHorse())); //Changed!
                s.setA6        (toInt(v.getA6RankedHorse())); //Changed!
                s.setTips      (toInt(v.getTipsRankedHorse())); //Changed!

                RoiView roi = roiMap.get(v.getId()); //Changed!
                if (roi != null) { //Changed!
                    s.setRoiTotalt (roi.getRoiTotalt()); //Changed!
                    s.setRoiSinceDayOne(roi.getRoiSinceDayOne()); //Changed!
                    s.setRoiVinnare(roi.getRoiVinnare()); //Changed!
                    s.setRoiPlats  (roi.getRoiPlats()); //Changed!
                    s.setRoiTrio   (roi.getRoiTrio()); //Changed!
                    s.setResultat  (roi.getResultat()); //Changed!
                } //Changed!

                processed++; //Changed!
            } //Changed!

            trackRepo.save(existingTrackMap.get(key)); //Changed!
            em.flush(); //Changed!
            em.clear(); //Changed!
        } //Changed!

        return new ResponseEntity<>( //Changed!
                "Processed " + processed + " horses for date " + requestedDate, //Changed!
                HttpStatus.CREATED //Changed!
        ); //Changed!
    } //Changed!

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

    private static LocalDate parseRequestedDate(Integer raw) { //Changed!
        if (raw == null) throw new IllegalArgumentException("date is null"); //Changed!
        String s = String.valueOf(raw).trim(); //Changed!
        if (s.length() == 8) return LocalDate.parse(s, BASIC); //Changed!
        if (s.length() == 6) return LocalDate.parse(s, YYMMDD); //Changed!
        throw new IllegalArgumentException("Unsupported date format: " + s); //Changed!
    } //Changed!

    private static LocalDate toLocalDateFlexible(Integer rawDate) { //Changed!
        if (rawDate == null) return LocalDate.MIN; //Changed!
        String s = String.valueOf(rawDate).trim(); //Changed!
        if (s.length() == 8) return LocalDate.parse(s, BASIC); //Changed!
        if (s.length() == 6) return LocalDate.parse(s, YYMMDD); //Changed!
        return toLocalDate(rawDate); //Changed!
    } //Changed!

    private static Set<Integer> sourceDateCandidates(LocalDate d) { //Changed!
        Set<Integer> out = new LinkedHashSet<>(); //Changed!
        out.add(Integer.parseInt(d.format(BASIC))); //Changed!
        out.add(Integer.parseInt(d.format(YYMMDD))); //Changed!
        return out; //Changed!
    } //Changed!

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


