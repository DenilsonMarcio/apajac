package com.apajac.acolhimento.domain.dtos;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ResponsavelDTO {
    private String nome;
    private String tipoParentesco;
    private List<ContatoDTO> contatos;
}
