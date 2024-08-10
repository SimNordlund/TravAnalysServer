package com.example.travanalysserver.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Competition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nameOfCompetition;

    @ManyToOne //Måste vara many för att det ska gå att ha flera tävlingar på ex. Romme. Byt till 1-1 vid behov
    @JoinColumn
    private Track track;
}
