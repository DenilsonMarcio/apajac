package com.apajac.acolhimento.domain.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UsuarioLogadoDTO {
    private String nome;
    private String role;
}
