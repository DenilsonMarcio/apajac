package br.com.apajac.acolhimento.domain.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UsuarioDTO {
    private String name;
    private String type;
    private int status;
    private String password;
    private String login;
}
