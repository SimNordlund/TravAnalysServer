package com.example.travanalysserver.data;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;

//@ComponentScan behövs denna?
public class FetchData implements CommandLineRunner {


    @Override
    public void run(String... args) {
        System.out.println("Hello");
    }

}
