package com.example.travanalysserver.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

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

    @Column(name = "tips")
    private int tips;

    @Column(name = "roi_totalt",  precision = 12, scale = 2)
    private BigDecimal roiTotalt;

    @Column(name = "roi_vinnare", precision = 12, scale = 2)
    private BigDecimal roiVinnare;

    @Column(name = "roi_plats",   precision = 12, scale = 2)
    private BigDecimal roiPlats;

    @Column(name = "roi_trio",    precision = 12, scale = 2)
    private BigDecimal roiTrio;

    @Column(name = "resultat")
    private int resultat;

    @OneToOne(mappedBy = "fourStarts", cascade = CascadeType.ALL, orphanRemoval = true)
    private CompleteHorse completeHorse;
}
