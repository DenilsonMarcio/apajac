package com.apajac.acolhimento.domain.dtos;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UsuarioLogadoDTO {
    private String nome;
    private String login;
    private List<String> roles;
}
