package com.example.travanalysserver.dto.starts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FourStartsDTO {
    private Long id;
    private int analys;
    private int fart;
    private int styrka;
    private int klass;
    private int prispengar;
    private int kusk;
    private int tips;
    private int placering;
    private int form;

    private int a1;
    private int a2;
    private int a3;
    private int a4;
    private int a5;
}
