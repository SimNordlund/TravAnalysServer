package com.example.travanalysserver.data;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;
import java.util.List;

//@ComponentScan behövs denna?
public class FetchData implements CommandLineRunner {


    @Override
    public void run(String... args) throws IOException {
        DataReader dr = new DataReader("C:\\Users\\TaraR\\IdeaProjects\\TravAnalysServer\\src\\main\\java\\com\\example\\travanalysserver\\data\\pissnisse.txt");

        List<String> testList = dr.getListOfData();
        System.out.println(testList);
    }

}
