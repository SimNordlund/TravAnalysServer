package com.example.travanalysserver.entitysec;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.Immutable;

import java.time.LocalDateTime;

@Entity
@Table(name = "rank")
@Immutable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RankHorse {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "startdatum")
    private Integer dateRankedHorse;

    @Column(name = "bankod")
    private String trackRankedHorse;

    @Column(name = "lopp")
    private String lapRankedHorse;

    @Column(name = "nr")
    private Integer nr;

    @Column(name = "namn")
    private String nameRankedHorse;

    @Column(name = "procentanalys")
    private String analysRankedHorse;

    @Column(name = "procenttid")
    private String tidRankedHorse;

    @Column(name = "procentprestation")
    private String prestationRankedHorse;

    @Column(name = "procentmotstand")
    private String motstandRankedHorse;

    @Column(name = "procentklass")
    private String prispengarRankedHorse;

    @Column(name ="procentskrik")
    private String stallSkrikRankedHorse;

    @Column(name = "procentplacering" )
    private String placeringRankedHorse;

    @Column(name = "procentform")
    private String formRankedHorse;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name ="tips")
    private int tipsRankedHorse;

    @Column(name = "spelform")
    private String competitionRankedHorse;
}
