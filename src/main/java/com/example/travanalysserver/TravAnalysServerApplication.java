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

@SpringBootApplication
public class TravAnalysServerApplication {

    @Autowired
    RoleAndUserDataSeeder roleAndUserDataSeeder;

    public static void main(String[] args) {
        SpringApplication.run(TravAnalysServerApplication.class, args);
    }

  /*  @Bean
    public CommandLineRunner demo(HorseRepo horseRepo, RaceRepo raceRepo, LapRepo lapRepo, RadarHorseRepo radarHorseRepo, RaceTypeRepo raceTypeRepo) {
        return args -> {
            Random rand = new Random();

            // Creating RaceTypes
            RaceType v75 = new RaceType(null, "v75", 7, new HashSet<>());
            RaceType v65 = new RaceType(null, "v65", 6, new HashSet<>());
            raceTypeRepo.saveAll(Arrays.asList(v75, v65));

            // Creating Races for each RaceType
            createRacesAndLapsForRaceType(v75, raceRepo, lapRepo, radarHorseRepo, rand);
            createRacesAndLapsForRaceType(v65, raceRepo, lapRepo, radarHorseRepo, rand);
        };
    } */
    /*
    private void createRacesAndLapsForRaceType(RaceType raceType, RaceRepo raceRepo, LapRepo lapRepo, RadarHorseRepo radarHorseRepo, Random rand) {
        for (int i = 0; i < raceType.getLapCount(); i++) {
            Race race = new Race();
            race.setRaceType(raceType);
            raceRepo.save(race);

            // Creating Laps for each Race
            createLapsForRace(race, lapRepo, radarHorseRepo, rand);
        }
    }

    private void createLapsForRace(Race race, LapRepo lapRepo, RadarHorseRepo radarHorseRepo, Random rand) {
        for (int i = 0; i < 5; i++) {  // Assuming each race has 5 laps
            Lap lap = new Lap();
            lap.setRace(race);
            lapRepo.save(lap);

            // Creating RadarHorses for each Lap
            createRadarHorsesForLap(lap, radarHorseRepo, rand);
        }
    }

    private void createRadarHorsesForLap(Lap lap, RadarHorseRepo radarHorseRepo, Random rand) {
        for (int i = 0; i < 10; i++) {  // Assuming each lap has 10 horses
            RadarHorse radarHorse = new RadarHorse(null, "Horse " + i + " in Lap " + lap.getId(),
                    rand.nextInt(100) + 1, rand.nextInt(100) + 1,
                    rand.nextInt(100) + 1, rand.nextInt(100) + 1,
                    rand.nextInt(100) + 1);
            radarHorse.setLap(lap);
            radarHorseRepo.save(radarHorse);
        }
    }
    /*

    //Seedar in användare.
   /* @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            roleAndUserDataSeeder.Seed();
            // mailTemplateSeeder.Seed();
        };
    }*/

}
