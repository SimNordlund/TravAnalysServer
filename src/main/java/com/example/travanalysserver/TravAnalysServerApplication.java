package com.example.travanalysserver;

import com.example.travanalysserver.entity.*;
import com.example.travanalysserver.entity.testing.RadarHorse;
import com.example.travanalysserver.repository.*;
import com.example.travanalysserver.security.RoleAndUserDataSeeder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.UUID;

@SpringBootApplication
public class TravAnalysServerApplication {

    @Autowired
    RoleAndUserDataSeeder roleAndUserDataSeeder;

    public static void main(String[] args) {
        SpringApplication.run(TravAnalysServerApplication.class, args);
    }

  /*  @Bean
    public CommandLineRunner demo(RadarHorseRepo repository) {
        return args -> {
            Random rand = new Random();

            for (int i = 0; i < 30; i++) {
                RadarHorse horse = RadarHorse.builder()
                        .name("Horse " + UUID.randomUUID().toString().substring(0, 8))
                        .valueOne(rand.nextInt(100))
                        .valueTwo(rand.nextInt(100))
                        .valueThree(rand.nextInt(100))
                        .valueFour(rand.nextInt(100))
                        .valueFive(rand.nextInt(100))
                        .build();
                repository.save(horse);
            }
        };
    } */


    //Seedar in användare.
   /* @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            roleAndUserDataSeeder.Seed();
            // mailTemplateSeeder.Seed();
        };
    }*/

}
