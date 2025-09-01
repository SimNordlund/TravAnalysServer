package com.example.travanalysserver.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class EightStarts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    private int a1;
    private int a2;
    private int a3;
    private int a4;
    private int a5;
    private int a6;

    //TODO HUR BLIR DET MED ROI FÖR OLIKA SPEL JAO?

 /*   @Column(name = "tips")
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
    private int resultat; */

    @OneToOne(mappedBy = "eightStarts", cascade = CascadeType.ALL, orphanRemoval = true)
    private CompleteHorse completeHorse;
}
