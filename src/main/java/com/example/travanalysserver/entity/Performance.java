package com.example.travanalysserver.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Performance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "horse_id", nullable = false)
    private Horse horse;
    @ManyToOne
    @JoinColumn(name = "lap_id", nullable = false)
    private Lap lap;
    private int number;
    private double odds;

}
