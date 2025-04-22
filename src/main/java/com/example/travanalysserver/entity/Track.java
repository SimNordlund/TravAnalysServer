package com.example.travanalysserver.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Track {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private String nameOfTrack;

    @OneToMany(mappedBy = "track", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Competition> competitions = new ArrayList<>();

    @Override
    public String toString() {
        return "Track{" +
                "id=" + id +
                ", date=" + date +
                ", nameOfTrack='" + nameOfTrack + '\'' +
                '}';
    }

}

