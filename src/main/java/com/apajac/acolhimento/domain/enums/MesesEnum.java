package com.apajac.acolhimento.domain.enums;

import lombok.Getter;

@Getter
public enum MesesEnum {
    JANEIRO(1),
    FEVEREIRO(2),
    MARÇO(3),
    ABRIL(4),
    MAIO(5),
    JUNHO(6),
    JULHO(7),
    AGOSTO(8),
    SETEMBRO(9),
    OUTUBRO(10),
    NOVEMBRO(11),
    DEZEMBRO(12);

    private final Integer values;
    MesesEnum(Integer values) {
        this.values = values;
    }
}
