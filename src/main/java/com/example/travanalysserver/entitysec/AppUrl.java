package com.example.travanalysserver.entitysec;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "AppUrl", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AppUrl {
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "url")
    private String url;
    @Column(name = "avd1") //Varje avd1 här avser en sträng av de top 3 bästa hästarna i varje lopp i bestämt spel
    private String avd1;
    @Column(name = "avd2")
    private String avd2;
    @Column(name = "avd3")
    private String avd3;
    @Column(name = "avd4")
    private String avd4;
    @Column(name = "avd5")
    private String avd5;
    @Column(name = "avd6")
    private String avd6;
    @Column(name = "avd7")
    private String avd7;
    @Column(name = "avd8")
    private String avd8;
    @Column(name = "date")
    private String date;
}
