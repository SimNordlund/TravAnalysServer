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
    private String nameOfCompleteHorse;

    @ManyToOne
    @JoinColumn
    private Lap lap;

    @OneToOne
    @JoinColumn
    private FourStarts fourStarts;

    @OneToOne
    @JoinColumn
    private EightStarts eightStarts;

    @OneToOne
    @JoinColumn
    private TwelveStarts twelveStarts;
}
