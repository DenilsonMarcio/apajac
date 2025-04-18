package com.apajac.acolhimento.domain.dtos.relatorio;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class PorIdadeIngressoDTO {
    Long id;
    String nome;
    String idade;
}