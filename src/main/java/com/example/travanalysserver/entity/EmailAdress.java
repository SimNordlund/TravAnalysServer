package com.example.travanalysserver.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
    @Column(unique = true)
    private String email;

    @Pattern(regexp = "^[0-9+\\- ]{6,20}$")
    @Column(unique = true)
    private String phone;
}
