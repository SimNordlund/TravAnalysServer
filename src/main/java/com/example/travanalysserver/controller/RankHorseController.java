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
    //Testing
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

    @GetMapping("/print")
    @Transactional
    public ResponseEntity<String> saveAllRanked() {

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

        Set<LocalDate> dates = workList.stream()
                .map(v -> toLocalDate(v.getDateRankedHorse()))
                .collect(Collectors.toSet());

        Set<String> names = workList.stream()
                .map(v -> BANKOD_TO_TRACK
                        .getOrDefault(v.getTrackRankedHorse(), v.getTrackRankedHorse()))
                .collect(Collectors.toSet());

        List<Track> existing = trackRepo.findAllByDateInAndNameOfTrackIn(dates, names);
        Map<String,Track>        trackMap = new HashMap<>();
        Map<String,Competition>  compMap  = new HashMap<>();
        Map<String,Lap>          lapMap   = new HashMap<>();
        Map<String,CompleteHorse> horseMap = new HashMap<>();

        for (Track t: existing) {
            String tKey = t.getDate() + "|" + t.getNameOfTrack();
            trackMap.put(tKey, t);
            for (Competition c: t.getCompetitions()) {
                String compName = normalizeCompetition(c.getNameOfCompetition());
                String compKey  = tKey + "|" + compName;
                compMap.put(compKey, c);
                for (Lap l: c.getLaps()) {
                    String lKey = compKey + "|" + l.getNameOfLap();
                    lapMap.put(lKey, l);
                    for (CompleteHorse h: l.getHorses()) {
                        String hKey = lKey + "|" + h.getNumberOfCompleteHorse();
                        horseMap.put(hKey, h);
                    }
                }
            }
        }

        int processed = 0;

        for (RankHorseView rankHorseView: workList) {
            LocalDate date = toLocalDate(rankHorseView.getDateRankedHorse());
            String trackName = BANKOD_TO_TRACK
                    .getOrDefault(rankHorseView.getTrackRankedHorse(), rankHorseView.getTrackRankedHorse());
            String tKey = date + "|" + trackName;

            Track track = trackMap.computeIfAbsent(tKey, k -> {
                Track tr = new Track(); tr.setDate(date); tr.setNameOfTrack(trackName);
                return tr;
            });

            String compName = normalizeCompetition(
                    rankHorseView.getCompetitionRankedHorse());
            String compKey = tKey + "|" + compName;

            Competition comp = compMap.computeIfAbsent(compKey, k -> {
                Competition c = new Competition();
                c.setNameOfCompetition(compName);
                c.setTrack(track); track.getCompetitions().add(c); return c;
            });

            String lKey = compKey + "|" + rankHorseView.getLapRankedHorse();
            Lap lap = lapMap.computeIfAbsent(lKey, k -> {
                Lap l = new Lap(); l.setNameOfLap(rankHorseView.getLapRankedHorse());
                l.setCompetition(comp); comp.getLaps().add(l); return l;
            });

            String hKey = lKey + "|" + rankHorseView.getNr();
            CompleteHorse horse = horseMap.computeIfAbsent(hKey, k -> {
                CompleteHorse h = new CompleteHorse();
                h.setNumberOfCompleteHorse(rankHorseView.getNr());
                h.setLap(lap); lap.getHorses().add(h); return h;
            });

            horse.setNameOfCompleteHorse(rankHorseView.getNameRankedHorse());

            //TODO LOgging ist för syst print

            int starts = toInt(rankHorseView.getStarterRankedHorse());

            if (starts == 9) {
                FourStarts fs = horse.getFourStarts();
                if (fs == null) {
                    fs = new FourStarts();
                    fs.setCompleteHorse(horse);
                    horse.setFourStarts(fs);
                }

                fs.setAnalys    (toInt(rankHorseView.getAnalysRankedHorse()));
                fs.setFart      (toInt(rankHorseView.getTidRankedHorse()));
                fs.setStyrka    (toInt(rankHorseView.getPrestationRankedHorse()));
                fs.setKlass     (toInt(rankHorseView.getMotstandRankedHorse()));
                fs.setPrispengar(toInt(rankHorseView.getPrispengarRankedHorse()));
                fs.setKusk      (toInt(rankHorseView.getStallSkrikRankedHorse()));
                fs.setPlacering (toInt(rankHorseView.getPlaceringRankedHorse()));
                fs.setForm      (toInt(rankHorseView.getFormRankedHorse()));
                fs.setTips      (toInt(rankHorseView.getTipsRankedHorse()));
                fs.setStarter   (starts);
                fs.setA1        (toInt(rankHorseView.getA1RankedHorse()));
                fs.setA2        (toInt(rankHorseView.getA2RankedHorse()));
                fs.setA3        (toInt(rankHorseView.getA3RankedHorse()));
                fs.setA4        (toInt(rankHorseView.getA4RankedHorse()));
                fs.setA5        (toInt(rankHorseView.getA5RankedHorse()));
                fs.setA6        (toInt(rankHorseView.getA6RankedHorse()));

                RoiView roi = roiMap.get(rankHorseView.getId());
                if (roi != null) {
                    fs.setRoiTotalt (roi.getRoiTotalt());
                    fs.setRoiVinnare(roi.getRoiVinnare());
                    fs.setRoiPlats  (roi.getRoiPlats());
                    fs.setRoiTrio   (roi.getRoiTrio());
                    fs.setResultat  (roi.getResultat());
                }

            }  if (starts == 2) {
                EightStarts es = horse.getEightStarts();
                if (es == null) {
                    es = new EightStarts();
                    es.setCompleteHorse(horse);
                    horse.setEightStarts(es);
                }

                es.setAnalys    (toInt(rankHorseView.getAnalysRankedHorse()));
                es.setFart      (toInt(rankHorseView.getTidRankedHorse()));
                es.setStyrka    (toInt(rankHorseView.getPrestationRankedHorse()));
                es.setKlass     (toInt(rankHorseView.getMotstandRankedHorse()));
                es.setPrispengar(toInt(rankHorseView.getPrispengarRankedHorse()));
                es.setKusk      (toInt(rankHorseView.getStallSkrikRankedHorse()));
                es.setPlacering (toInt(rankHorseView.getPlaceringRankedHorse()));
                es.setForm      (toInt(rankHorseView.getFormRankedHorse()));
                es.setStarter   (starts);
                es.setA1        (toInt(rankHorseView.getA1RankedHorse()));
                es.setA2        (toInt(rankHorseView.getA2RankedHorse()));
                es.setA3        (toInt(rankHorseView.getA3RankedHorse()));
                es.setA4        (toInt(rankHorseView.getA4RankedHorse()));
                es.setA5        (toInt(rankHorseView.getA5RankedHorse()));
                es.setA6        (toInt(rankHorseView.getA6RankedHorse()));

            }
            else {
                System.out.println("Något stämmer inte just nu");
            }
            processed++;
        }

        trackRepo.saveAll(trackMap.values());
        syncMetaRepo.save(new SyncMeta("ranked_horses", LocalDateTime.now()));

        return new ResponseEntity<>(
                "Processed " + processed + " horses since " + lastRun,
                HttpStatus.CREATED
        );
    }

    private static LocalDate toLocalDate(Integer yyyymmdd) {
        if (yyyymmdd == null) return LocalDate.MIN;
        return LocalDate.parse(String.format("%08d", yyyymmdd), BASIC);
    }

    private static int toInt(String s) {
        if (s == null) return 0;
        String t = s.trim().replace("%", "").replace(",", ".");
        try { return (int) Math.round(Double.parseDouble(t)); } catch (Exception e) { return 0; }
    }

    private static String normalizeCompetition(String s) {
        if (s == null) return "Vinnare";
        String v = s.trim();
        return v.isEmpty() ? "Vinnare" : v;
    }
}
