package com.example.travanalysserver.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class EmailAdress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email(message = "Ogiltig mejladress")
    @NotNull(message = "Mejladress får inte vara null")
    @Column(nullable = false, unique = true)
    private String email;
}
