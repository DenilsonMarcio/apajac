package br.com.apajac.acolhimento.domain.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UsuarioDTO {
    private String name;
    private String role;
    private Character status;
    private String login;
    private String password;
}
