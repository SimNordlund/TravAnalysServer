package com.example.travanalysserver.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CompleteHorse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int numberOfCompleteHorse;
    private String nameOfCompleteHorse;

    @ManyToOne
    @JoinColumn(name = "lap_id")
    private Lap lap;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "four_starts_id")
    private FourStarts fourStarts;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "eight_starts_id")
    private EightStarts eightStarts;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "twelve_starts_id")
    private TwelveStarts twelveStarts;
}
