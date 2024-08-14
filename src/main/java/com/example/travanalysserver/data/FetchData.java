package com.example.travanalysserver.data;

import com.example.travanalysserver.entity.Track;
import com.example.travanalysserver.repository.TrackRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

public class FetchData implements CommandLineRunner {


    //private TrackRepo trackRepo;

    //FIXA JACKSON DEPENDENCY XD

    @Override
    public void run(String... args) throws IOException {

        System.out.println("Hello");

      /*  ObjectMapper objectMapper = new ObjectMapper();
        File jsonFile = new File("C:\\Users\\TaraR\\IdeaProjects\\TravAnalysServer\\src\\main\\java\\com\\example\\travanalysserver\\data\\test2");

        Track track = objectMapper.readValue(jsonFile, Track.class);
        trackRepo.save(track); */

     //   DataReader dr = new DataReader("C:\\Users\\TaraR\\IdeaProjects\\TravAnalysServer\\src\\main\\java\\com\\example\\travanalysserver\\data\\pissnisse.txt");
     //   List<String> testList = dr.getListOfData();
     //   System.out.println(testList);
    }

}
