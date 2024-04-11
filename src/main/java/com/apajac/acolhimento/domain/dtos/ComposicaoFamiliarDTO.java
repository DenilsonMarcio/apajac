package com.apajac.acolhimento.domain.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ComposicaoFamiliarDTO {
    private String nome;
    private Integer anoNascimento;
    private String parentesco;
    private String ocupacao;
}
