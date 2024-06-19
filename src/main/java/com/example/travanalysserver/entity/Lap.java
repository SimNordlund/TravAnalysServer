package com.example.travanalysserver.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Lap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "race_id", nullable = false)
    private Race race;
    private int number;

    @OneToMany(mappedBy = "lap", cascade = CascadeType.ALL)
    private Set<Performance> performances;
}

