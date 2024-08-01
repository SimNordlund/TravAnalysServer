package com.example.travanalysserver.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TwelveStarts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
