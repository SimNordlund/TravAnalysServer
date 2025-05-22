package com.example.travanalysserver.entitysec;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.Immutable;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "roi")
@Immutable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Roi {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "rankid")
    private Long rankId;

    @Column(name = "roitotalt")
    private BigDecimal roiTotalt;

    @Column(name = "roivinnare")
    private BigDecimal roiVinnare;

    @Column(name = "roiplats")
    private BigDecimal roiPlats;

    @Column(name = "roitrio")
    private BigDecimal roiTrio;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "resultat")
    private int resultat;

}
