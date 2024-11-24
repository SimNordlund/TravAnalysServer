package com.example.travanalysserver.entity;

import com.example.travanalysserver.entity.testing.RadarHorse;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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
    private String nameOfLap;

    @ManyToOne
    @JoinColumn(name = "competition_id")
    private Competition competition;

    @OneToMany(mappedBy = "lap", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompleteHorse> horses = new ArrayList<>();
}

