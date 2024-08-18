package com.example.travanalysserver;

import com.example.travanalysserver.entity.*;
import com.example.travanalysserver.service.interfaces.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;

@ComponentScan
public class FetchData implements CommandLineRunner {


    private final TrackService trackService;
    private final CompetitionService competitionService;
    private final LapService lapService;
    private final CompleteHorseService completeHorseService;
    private final FourStartsService fourStartsService;

    public FetchData (TrackService trackService, CompetitionService competitionService,
                      LapService lapService, CompleteHorseService completeHorseService,
                      FourStartsService fourStartsService){
        this.trackService = trackService;
        this.competitionService = competitionService;
        this.lapService = lapService;
        this.completeHorseService = completeHorseService;
        this.fourStartsService = fourStartsService;
    }

    @Override
    public void run(String... args) throws IOException {

        Track trackFromJson = trackService.getTrackFromJsonFile();

        Competition competitionFromJson = competitionService.getCompetitionFromJsonFile();
        competitionFromJson.setTrack(trackFromJson);

        Lap[] lapsFromJson = lapService.getLapsFromJsonFile();
        for (Lap lap : lapsFromJson) {
            lap.setCompetition(competitionFromJson);
        }

        CompleteHorse[] completeHorsesFromJson = completeHorseService.getCompleteHorsesFromJsonFile();
        for (int i = 0; i < completeHorsesFromJson.length; i++){
            if (i < 10) {
                completeHorsesFromJson[i].setLap(lapsFromJson[0]);
            }
            else {
                completeHorsesFromJson[i].setLap(lapsFromJson[1]);
            }
        }

        FourStarts[] fourStartsFromJson = fourStartsService.getFourStartsFromJsonFile();
        for (int i = 0; i < fourStartsFromJson.length; i++){
            if (i < 10) {
                completeHorsesFromJson[i].setFourStarts(fourStartsFromJson[i]);
            }
            else {
                completeHorsesFromJson[i].setFourStarts(fourStartsFromJson[i]);
            }
        }


        String responsTrack = trackService.saveDownTrackToDB(trackFromJson);
        String responsCompetition = competitionService.saveDownCompetitionToDB(competitionFromJson);
        String responsLaps = lapService.saveDownLapsToDB(lapsFromJson);
        String responsFourStarts = fourStartsService.saveDownCompleteFourStartsToDB(fourStartsFromJson);
        String responsCompleteHorses = completeHorseService.saveDownCompleteHorsesToDB(completeHorsesFromJson);

        System.out.println(responsTrack);
        System.out.println(responsCompetition);
        System.out.println(responsLaps);
        System.out.println(responsCompleteHorses);
        System.out.println(responsFourStarts);
        System.out.println("Hello, this is the end");


     //   DataReader dr = new DataReader("C:\\Users\\TaraR\\IdeaProjects\\TravAnalysServer\\src\\main\\java\\com\\example\\travanalysserver\\data\\pissnisse.txt");
     //   List<String> testList = dr.getListOfData();
     //   System.out.println(testList);
    }

}
