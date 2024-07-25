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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String raceType;
    private int lapCount;

    //@JsonIgnore
    @OneToMany(mappedBy = "raceType")
    private Set<Race> races = new HashSet<>();
}
