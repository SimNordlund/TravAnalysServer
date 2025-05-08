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
    private Long id;  // Changed!: raw bigint → Long

    @Column(name = "startdatum")
    private Integer dateRankedHorse;  // Changed!: raw integer → Integer

    @Column(name = "bankod")
    private String trackRankedHorse;  // Changed!: varchar → String

    @Column(name = "lopp")
    private String lapRankedHorse;    // Changed!: varchar → String

    @Column(name = "nr")
    private Integer nr;               // raw integer → Integer

    @Column(name = "namn")
    private String nameRankedHorse;   // Changed!: varchar → String

    @Column(name = "procentanalys")
    private String analysRankedHorse; // Changed!: varchar → String

    @Column(name = "procenttid")
    private String tidRankedHorse;    // Changed!: varchar → String

    @Column(name = "procentprestation")
    private String prestationRankedHorse; // Changed!: varchar → String

    @Column(name = "procentmotstand")
    private String motstandRankedHorse;   // Changed!: varchar → String

    @Column(name = "procentklass")
    private String prispengarRankedHorse;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
