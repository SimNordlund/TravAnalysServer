package com.example.travanalysserver.entitysec;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.Immutable;


import java.time.LocalDate;

@Entity
@Table(name = "\"Rank\"")         // UNQUOTED GÅR HA INSTÄLLNING I PROPERTY MEN GÖR SÅ HÄR SÅ LÄNGE
@Immutable


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RankHorse {
    @Id
    @Column(name = "\"Nr\"")
    private Long id; // KOLLA MED FAR VAR ÄR ID?

    @Column(name = "\"Startdatum\"")
    private Integer dateRankedHorse;

    @Column(name = "\"Bankod\"")
    private String trackRankedHorse;

    @Column(name = "\"Typ\"")
    private String competitionRankedHorse;

    @Column(name = "\"Lopp\"")
    private String lapRankedHorse;

    @Column(name = "\"Namn\"")
    private String nameRankedHorse;

    @Column(name = "\"Rank\"")
    private String analysRankedHorse;

    @Column(name = "\"Tid\"")
    private String tidRankedHorse;

    @Column(name = "\"Procent\"")
    private String prestationRankedHorse;

    @Column(name = "\"Odds\"")
    private String motstandRankedHorse;

}

