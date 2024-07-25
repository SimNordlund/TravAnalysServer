package com.example.travanalysserver.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Race {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date; //Ex 2024-07-25
    private String location; //Ex. Solvalla

    @OneToMany(mappedBy = "race", cascade = CascadeType.ALL)
    private Set<Lap> laps;
}
