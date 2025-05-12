package com.example.travanalysserver.entitysec;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface RoiView {

    Long getId();
    Long        getRankId();
    BigDecimal  getRoiTotalt();
    BigDecimal getRoiVinnare();
    BigDecimal  getRoiPlats();
    BigDecimal  getRoiTrio();

    LocalDateTime getUpdatedAt();
}
