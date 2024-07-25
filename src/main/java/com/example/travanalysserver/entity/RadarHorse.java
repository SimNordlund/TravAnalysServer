package com.example.travanalysserver.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RadarHorse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int valueOne;
    private int valueTwo;
    private int valueThree;
    private int valueFour;
    private int valueFive;

    @ManyToOne
    @JoinColumn(name = "lap_id")
    private Lap lap;

    public RadarHorse (Long id, String name, int valueOne, int valueTwo, int valueThree, int valueFour, int valueFive){
        this.id = id;
        this.name = name;
        this.valueOne = valueOne;
        this.valueTwo = valueTwo;
        this.valueThree = valueThree;
        this.valueFour = valueFour;
        this.valueFive = valueFive;
    }

}
