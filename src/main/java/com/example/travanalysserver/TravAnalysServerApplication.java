package com.example.travanalysserver;

import com.example.travanalysserver.entity.*;
import com.example.travanalysserver.entity.testing.RadarHorse;
import com.example.travanalysserver.repository.*;
import com.example.travanalysserver.security.RoleAndUserDataSeeder;
import com.example.travanalysserver.service.interfaces.LapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.*;

@SpringBootApplication
public class TravAnalysServerApplication {

    @Autowired
    RoleAndUserDataSeeder roleAndUserDataSeeder;

    public static void main(String[] args) {
        SpringApplication.run(TravAnalysServerApplication.class, args);
    }

    //Seedar in testRadar hästar
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
    }

    //Seedar in tracks + competition
    @Bean
    public CommandLineRunner demo2(TrackRepo trackRepo, CompetitionRepo competitionRepo,
                                   LapRepo lapRepo, CompleteHorseRepo completeHorseRepo, FourStartsRepo fourStartsRepo) {
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

            //Seedar in lopp
            Lap lap1 = new Lap(null, "Avd 1", comp);
            Lap lap2 = new Lap(null, "Avd 2", comp);
            Lap lap3 = new Lap(null, "Avd 3", comp);
            Lap lap4 = new Lap(null, "Avd 4", comp);

            Lap[] lapsArray = new Lap[]{lap1, lap2, lap3, lap4};

            for (int i = 0; i < 4; i++){
                lapRepo.save(lapsArray[i]);
            }

            //Seedar in hästar
            List <CompleteHorse> tempListHorses =  new ArrayList<>();

            FourStarts fs1 = new FourStarts(null, 10, 20, 30, 44, 55, 66, 78);
            FourStarts fs2 = new FourStarts(null, 33, 22, 55, 99, 22, 55, 33);
            FourStarts fs3 = new FourStarts(null, 15, 22, 37, 24, 55, 36, 27);
            FourStarts fs4 = new FourStarts(null, 51, 22, 83, 14, 35, 56, 27);
            FourStarts fs5 = new FourStarts(null, 81, 72, 23, 14, 55, 66, 17);

            tempListHorses.add( new CompleteHorse(null, "1. Knut", lap2, fs1, null, null));
            tempListHorses.add(new CompleteHorse(null, "2. PissNisse", lap2, fs2, null, null));
            tempListHorses.add(new CompleteHorse(null, "3. Bengt", lap2, fs3, null, null));
            tempListHorses.add(new CompleteHorse(null, "4. Racer Ryssen", lap2, fs4, null, null));
            tempListHorses.add(new CompleteHorse(null, "5. Sören swug", lap2, fs5, null, null));

            fourStartsRepo.save(fs1);
            fourStartsRepo.save(fs2);
            fourStartsRepo.save(fs3);
            fourStartsRepo.save(fs4);
            fourStartsRepo.save(fs5);

            completeHorseRepo.saveAll(tempListHorses); //Sparar hästar

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
