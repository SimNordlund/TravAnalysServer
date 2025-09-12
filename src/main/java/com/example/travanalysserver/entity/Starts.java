package com.example.travanalysserver.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(
        name = "starts",
        uniqueConstraints = @UniqueConstraint(columnNames = {"complete_horse_id","starter"})
)
@NoArgsConstructor @AllArgsConstructor @Getter @Setter @Builder
public class Starts {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int analys;
    private int fart;
    private int styrka;
    private int klass;
    private int prispengar;
    private int kusk;
    private int placering;
    private int form;

    private int starter;

    private int a1,a2,a3,a4,a5,a6;

    private int tips;

    private BigDecimal roiTotalt;
    private BigDecimal roiSinceDayOne;
    private BigDecimal roiVinnare;
    private BigDecimal roiPlats;
    private BigDecimal roiTrio;
    private int resultat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "complete_horse_id")
    private CompleteHorse completeHorse;
}
