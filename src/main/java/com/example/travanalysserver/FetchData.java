package com.example.travanalysserver;

import com.example.travanalysserver.entity.*;
import com.example.travanalysserver.service.interfaces.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component // Marks this class as a Spring-managed component
public class FetchData implements CommandLineRunner {

    private final TrackService trackService;
    private final CompetitionService competitionService;
    private final LapService lapService;
    private final CompleteHorseService completeHorseService;
    private final FourStartsService fourStartsService;

    public FetchData(TrackService trackService, CompetitionService competitionService,
                     LapService lapService, CompleteHorseService completeHorseService,
                     FourStartsService fourStartsService) {
        this.trackService = trackService;
        this.competitionService = competitionService;
        this.lapService = lapService;
        this.completeHorseService = completeHorseService;
        this.fourStartsService = fourStartsService;
    }

    @Override
    public void run(String... args) throws IOException {
        // This code will run at startup and seed data into the database

        Track trackFromJson = trackService.getTrackFromJsonFile();

        Competition competitionFromJson = competitionService.getCompetitionFromJsonFile();
        competitionFromJson.setTrack(trackFromJson);

        Lap[] lapsFromJson = lapService.getLapsFromJsonFile();
        for (Lap lap : lapsFromJson) {
            lap.setCompetition(competitionFromJson);
        }

        CompleteHorse[] completeHorsesFromJson = completeHorseService.getCompleteHorsesFromJsonFile();
        for (int i = 0; i < completeHorsesFromJson.length; i++) {
            if (i < 10) {
                completeHorsesFromJson[i].setLap(lapsFromJson[0]);
            } else {
                completeHorsesFromJson[i].setLap(lapsFromJson[1]);
            }
        }

        FourStarts[] fourStartsFromJson = fourStartsService.getFourStartsFromJsonFile();
        for (int i = 0; i < fourStartsFromJson.length; i++) {
            completeHorsesFromJson[i].setFourStarts(fourStartsFromJson[i]);
        }

        // Save the fetched data to the database
        String responsTrack = trackService.saveDownTrackToDB(trackFromJson);
        String responsCompetition = competitionService.saveDownCompetitionToDB(competitionFromJson);
        String responsLaps = lapService.saveDownLapsToDB(lapsFromJson);
        String responsFourStarts = fourStartsService.saveDownCompleteFourStartsToDB(fourStartsFromJson);
        String responsCompleteHorses = completeHorseService.saveDownCompleteHorsesToDB(completeHorsesFromJson);

        // Print the results to the console
        System.out.println(responsTrack);
        System.out.println(responsCompetition);
        System.out.println(responsLaps);
        System.out.println(responsCompleteHorses);
        System.out.println(responsFourStarts);
        System.out.println("Data seeding completed.");
    }
}
