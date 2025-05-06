package com.example.travanalysserver.dto.travanalys;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SendEverythingDTO {
    private String nameOfTrack;
    private LocalDate date;
    private List<CompetitionDTO> competitions;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CompetitionDTO {
        private String nameOfCompetition;
        private List<LapDTO> laps;

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        public static class LapDTO {
            private String nameOfLap;
            private List<CompleteHorseDTO> horses;

            @Getter
            @Setter
            @NoArgsConstructor
            @AllArgsConstructor
            @Builder
            public static class CompleteHorseDTO {
                private String nameOfCompleteHorse;
                private int numberOfCompleteHorse;
                private FourStartsDTO fourStarts;


                @Getter
                @Setter
                @NoArgsConstructor
                @AllArgsConstructor
                @Builder
                public static class FourStartsDTO {
                    private int analys;
                    private int fart;
                    private int styrka;
                    private int klass;
                    private int prispengar;
                    private int kusk;
                    private int spar;
                }
            }
        }
    }
}

