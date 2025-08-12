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
    private int kusk; // KUSKEN FÅR VARA STALLSKRIK
    private int tips;
    //private int travskrik? Eller använda kusk? Kommer behöva fler jao!!
}
