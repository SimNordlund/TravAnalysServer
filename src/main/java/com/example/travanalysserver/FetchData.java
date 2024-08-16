package com.example.travanalysserver;

import com.example.travanalysserver.entity.Competition;
import com.example.travanalysserver.entity.Lap;
import com.example.travanalysserver.entity.Track;
import com.example.travanalysserver.service.interfaces.CompetitionService;
import com.example.travanalysserver.service.interfaces.LapService;
import com.example.travanalysserver.service.interfaces.TrackService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;

@ComponentScan
public class FetchData implements CommandLineRunner {


    private final TrackService trackService;
    private final CompetitionService competitionService;
    private final LapService lapService;

    public FetchData (TrackService trackService, CompetitionService competitionService, LapService lapService){
        this.trackService = trackService;
        this.competitionService = competitionService;
        this.lapService = lapService;
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


        String responsTrack  = trackService.saveDownTrackToDB(trackFromJson);
        String responsCompetition = competitionService.saveDownCompetitionToDB(competitionFromJson);
        String responsLaps = lapService.saveDownLapsToDB(lapsFromJson);

        System.out.println(responsTrack);
        System.out.println(responsCompetition);
        System.out.println(responsLaps);
        System.out.println("Hello, this is the end");


     //   DataReader dr = new DataReader("C:\\Users\\TaraR\\IdeaProjects\\TravAnalysServer\\src\\main\\java\\com\\example\\travanalysserver\\data\\pissnisse.txt");
     //   List<String> testList = dr.getListOfData();
     //   System.out.println(testList);
    }

}
