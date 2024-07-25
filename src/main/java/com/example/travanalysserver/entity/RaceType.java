package com.example.travanalysserver.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RaceType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Auto Increment
    private Long id;

    private String raceType; //Ex. v75
    private int lapCount; //Antal lopp, ex 7 på v75

    @OneToMany(mappedBy = "raceType")
    private Set<Race> races = new HashSet<>();
}
