package com.apajac.acolhimento.domain.dtos.relatorio;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class PorBairroDTO {
    Long id;
    String nome;
    String bairro;
}
