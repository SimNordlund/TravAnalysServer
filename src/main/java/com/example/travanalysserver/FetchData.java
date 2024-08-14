package com.example.travanalysserver;

import com.example.travanalysserver.entity.Track;
import com.example.travanalysserver.service.interfaces.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@ComponentScan
public class FetchData implements CommandLineRunner {


    TrackService trackService;

    public FetchData (TrackService trackService){
        this.trackService = trackService;
    }

    @Override
    public void run(String... args) throws IOException {


        Track[] trackArray = trackService.getTracksToArray();
        String respons  = trackService.saveDownAllTracksToDB(trackArray);
        System.out.println(respons);

        System.out.println("Hello, this is the end");


     //   DataReader dr = new DataReader("C:\\Users\\TaraR\\IdeaProjects\\TravAnalysServer\\src\\main\\java\\com\\example\\travanalysserver\\data\\pissnisse.txt");
     //   List<String> testList = dr.getListOfData();
     //   System.out.println(testList);
    }

}
