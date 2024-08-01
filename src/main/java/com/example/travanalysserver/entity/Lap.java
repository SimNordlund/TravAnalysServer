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
    @JoinColumn
    private Competition competition;
}

