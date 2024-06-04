package com.apajac.acolhimento.domain.dtos;

import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UsuarioSemSenhaDTO {
    private Long id;
    private String nome;
    private String login;
    private Boolean status;
    private Set<String> roles;
}
