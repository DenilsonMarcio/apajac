package com.apajac.acolhimento.domain.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UsuarioSemSenhaDTO {
    private Long id;
    private String nome;
    private String role;
    private String login;
    private Boolean status;
}
