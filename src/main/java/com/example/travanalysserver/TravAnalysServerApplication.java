package com.example.travanalysserver;

import com.example.travanalysserver.entity.*;
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
import java.util.List;
import java.util.Set;
import java.util.Random;

@SpringBootApplication
public class TravAnalysServerApplication {

    @Autowired
    RoleAndUserDataSeeder roleAndUserDataSeeder;

    public static void main(String[] args) {
        SpringApplication.run(TravAnalysServerApplication.class, args);
    }

  /*  @Bean
    public CommandLineRunner demo(HorseRepo horseRepo, RaceRepo raceRepo, LapRepo lapRepo, PerformanceRepo performanceRepo, RadarHorseRepo radarHorseRepo) {
        return args -> {

            Random rand = new Random();
            RadarHorse radarHorse1 = new RadarHorse(null, "1. Speedy Gonzi", rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1);
            RadarHorse radarHorse2 = new RadarHorse(null, "2. Ernst Pro", rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1);
            RadarHorse radarHorse3 = new RadarHorse(null, "3. Sabel Sigge", rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1);
            RadarHorse radarHorse4 = new RadarHorse(null, "4. Torsten", rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1);
            RadarHorse radarHorse5 = new RadarHorse(null, "5. Kapten Knut", rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1);
            RadarHorse radarHorse6 = new RadarHorse(null, "6. Piss-Nicke", rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1);
            RadarHorse radarHorse7 = new RadarHorse(null, "7. Soy-Simon", rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1);
            RadarHorse radarHorse8 = new RadarHorse(null, "8. Alice Van Dog", rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1);
            RadarHorse radarHorse9 = new RadarHorse(null, "9. Karin.KEKW", rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1);
            RadarHorse radarHorse10 = new RadarHorse(null, "10. Dr Tras", rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1);

            RadarHorse radarHorse11 = new RadarHorse(null, "11. Speedy Gonzi", rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1);
            RadarHorse radarHorse12 = new RadarHorse(null, "12. Ernst Pro", rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1);
            RadarHorse radarHorse13 = new RadarHorse(null, "13. Sabel Sigge", rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1);
            RadarHorse radarHorse14 = new RadarHorse(null, "14. Torsten", rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1);
            RadarHorse radarHorse15 = new RadarHorse(null, "15. Kapten Knut", rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1);
            RadarHorse radarHorse16 = new RadarHorse(null, "16. Piss-Nicke", rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1);
            RadarHorse radarHorse17 = new RadarHorse(null, "17. Soy-Simon", rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1);
            RadarHorse radarHorse18 = new RadarHorse(null, "18. Alice Van Dog", rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1);
            RadarHorse radarHorse19 = new RadarHorse(null, "19. Karin.KEKW", rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1);
            RadarHorse radarHorse20 = new RadarHorse(null, "20. Dr Tras", rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1);

            RadarHorse radarHorse21 = new RadarHorse(null, "21. Speedy Gonzi", rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1);
            RadarHorse radarHorse22 = new RadarHorse(null, "22. Ernst Pro", rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1);
            RadarHorse radarHorse23 = new RadarHorse(null, "23. Sabel Sigge", rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1);
            RadarHorse radarHorse24 = new RadarHorse(null, "24. Torsten", rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1);
            RadarHorse radarHorse25 = new RadarHorse(null, "25. Kapten Knut", rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1);
            RadarHorse radarHorse26 = new RadarHorse(null, "26. Piss-Nicke", rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1);
            RadarHorse radarHorse27 = new RadarHorse(null, "27. Soy-Simon", rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1);
            RadarHorse radarHorse28 = new RadarHorse(null, "28. Alice Van Dog", rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1);
            RadarHorse radarHorse29 = new RadarHorse(null, "29. Karin.KEKW", rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1);
            RadarHorse radarHorse30 = new RadarHorse(null, "30. Dr Tras", rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1, rand.nextInt(100) + 1);


            List<RadarHorse> radarHorses = Arrays.asList(radarHorse1, radarHorse2, radarHorse3, radarHorse4, radarHorse5, radarHorse6, radarHorse7, radarHorse8, radarHorse9, radarHorse10,
                    radarHorse11, radarHorse12, radarHorse13, radarHorse14, radarHorse15, radarHorse16, radarHorse17, radarHorse18, radarHorse19, radarHorse20,
                    radarHorse21, radarHorse22, radarHorse23, radarHorse24, radarHorse25, radarHorse26, radarHorse27, radarHorse28, radarHorse29, radarHorse30);
            radarHorseRepo.saveAll(radarHorses);


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


    }

    //Seedar in användare.
    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            roleAndUserDataSeeder.Seed();
            // mailTemplateSeeder.Seed();
        };
    } */

}
