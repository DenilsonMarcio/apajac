package com.apajac.acolhimento.enums;


public enum RolesEnum {
    MASTER("N0"),
    ADM_GERAL("N1"),
    ADM_CADASTRO("N2"),
    VOLUNTARIO("N3"),
    TEMPORARIO("N4");
    private final String role;

    RolesEnum(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public static RolesEnum fromValue(String value) {
        for (RolesEnum rolesEnum : values()) {
            if (rolesEnum.role.equals(value)) {
                return rolesEnum;
            }
        }
        throw new IllegalArgumentException("Invalid Role value: " + value);
    }
}


