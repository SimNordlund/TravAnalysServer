package com.example.travanalysserver.entitysec;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Banner", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Banner {
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "mening") private String mening;
    @Column(name = "url")    private String url;
}
