package com.apajac.acolhimento.domain.enums;

import lombok.Getter;

@Getter
public enum AuditoriaEnum {
    CREATED("C"),
    UPDATED("U"),
    DELETED("D");
    private final String values;
    AuditoriaEnum(String values) {
        this.values = values;
    }
}
