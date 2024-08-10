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

import java.time.LocalDate;
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

    //Seedar in testRadar hästar
/*   @Bean
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

    //Seedar in tracks + competition
 /*   @Bean
    public CommandLineRunner demo2(TrackRepo trackRepo, CompetitionRepo competitionRepo) {
        return args -> {
            Track track = new Track(null, LocalDate.of(2024, 8, 6), "Romme");
            Track track1 = new Track(null, LocalDate.of(2024, 8, 9), "Knuthöjden");
            Track track2 = new Track(null, LocalDate.of(2024, 8, 8), "Lill-sången");
            Track track3 = new Track(null, LocalDate.of(2024, 8, 7), "Färjestad");

            trackRepo.save(track);
            trackRepo.save(track1);
            trackRepo.save(track2);
            trackRepo.save(track3);

            Competition comp = new Competition(null, "v75", track3);
            Competition comp1 = new Competition(null, "v65", track2);
            Competition comp2 = new Competition(null, "v64", track1);
            Competition comp3 = new Competition(null, "v86", track);
            Competition comp4 = new Competition(null, "v86", track3);

            competitionRepo.save(comp);
            competitionRepo.save(comp1);
            competitionRepo.save(comp2);
            competitionRepo.save(comp3);
            competitionRepo.save(comp4);
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
