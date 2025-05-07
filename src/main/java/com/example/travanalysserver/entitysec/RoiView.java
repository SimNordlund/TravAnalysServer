package com.example.travanalysserver.entitysec;

import java.math.BigDecimal;

public interface RoiView {

    Long getId();
    Long        getRankId();        //Changed!
    BigDecimal  getRoiTotalt();     //Changed!
    BigDecimal getRoiVinnare();    //Changed!
    BigDecimal  getRoiPlats();      //Changed!
    BigDecimal  getRoiTrio();       //Changed!

}
