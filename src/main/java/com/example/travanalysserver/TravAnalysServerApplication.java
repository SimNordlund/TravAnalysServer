package com.example.travanalysserver;

import com.example.travanalysserver.entity.Horse;
import com.example.travanalysserver.entity.Lap;
import com.example.travanalysserver.entity.Performance;
import com.example.travanalysserver.entity.Race;
import com.example.travanalysserver.repository.HorseRepo;
import com.example.travanalysserver.repository.LapRepo;
import com.example.travanalysserver.repository.PerformanceRepo;
import com.example.travanalysserver.repository.RaceRepo;
import com.example.travanalysserver.security.RoleAndUserDataSeeder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class TravAnalysServerApplication {

    @Autowired
    RoleAndUserDataSeeder roleAndUserDataSeeder;

    public static void main(String[] args) {
        SpringApplication.run(TravAnalysServerApplication.class, args);
    }

 /*   @Bean
    public CommandLineRunner demo(HorseRepo horseRepo, RaceRepo raceRepo, LapRepo lapRepo, PerformanceRepo performanceRepo) {
        return args -> {
            // Create horses
            Horse horse1 = new Horse(null, "Lightning", new HashSet<>());
            Horse horse2 = new Horse(null, "Thunder", new HashSet<>());
            Horse horse3 = new Horse(null, "Storm", new HashSet<>());

            List<Horse> horses = Arrays.asList(horse1, horse2, horse3);
            horseRepo.saveAll(horses);

            // Create race
            Race race = new Race(null, LocalDate.now(), "Newmarket", new HashSet<>());

            // Create laps
            Lap lap1 = new Lap(null, race, 1, new HashSet<>());
            Lap lap2 = new Lap(null, race, 2, new HashSet<>());
            Set<Lap> laps = new HashSet<>(Arrays.asList(lap1, lap2));
            race.setLaps(laps);

            raceRepo.save(race);

            // Create performances
            Performance perf1 = new Performance(null, horse1, lap1, 1, 3.5);
            Performance perf2 = new Performance(null, horse2, lap1, 2, 2.0);
            Performance perf3 = new Performance(null, horse3, lap1, 3, 5.0);
            Performance perf4 = new Performance(null, horse1, lap2, 1, 3.0);
            Performance perf5 = new Performance(null, horse2, lap2, 2, 2.5);
            Performance perf6 = new Performance(null, horse3, lap2, 3, 4.5);

            performanceRepo.saveAll(Arrays.asList(perf1, perf2, perf3, perf4, perf5, perf6));
        };


    } */

    //Seedar in användare.
    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            roleAndUserDataSeeder.Seed();
            // mailTemplateSeeder.Seed();
        };
    }
}
