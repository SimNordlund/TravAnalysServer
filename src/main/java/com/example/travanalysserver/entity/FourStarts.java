package com.example.travanalysserver.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class FourStarts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int analys;
    private int fart;
    private int styrka;
    private int klass;
    private int prispengar;
    private int kusk;
    private int spar;

    @OneToOne(mappedBy = "fourStarts") //STÄMMER DENNA ELLER WTF? Vrf?
    private CompleteHorse completeHorse;
}
