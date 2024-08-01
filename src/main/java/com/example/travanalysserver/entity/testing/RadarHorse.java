package com.example.travanalysserver.entity.testing;

import com.example.travanalysserver.entity.Lap;
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

}
