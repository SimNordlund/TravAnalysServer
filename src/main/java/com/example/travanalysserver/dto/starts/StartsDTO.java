package com.example.travanalysserver.dto.starts;

import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class StartsDTO {
    private Long id;
    private int analys,fart,styrka,klass,prispengar,kusk,placering,form;
    private int starter;
    private int a1,a2,a3,a4,a5,a6;
    private java.math.BigDecimal roiTotalt, roiVinnare, roiPlats, roiTrio;
    private int resultat;
}
