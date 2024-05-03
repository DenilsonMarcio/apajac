package com.apajac.acolhimento.domain.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UsuarioDTO {
    private String nome;
    private String role;
    private Character status;
    private String login;
    private String password;
}
