package com.apajac.acolhimento.domain.dtos.relatorio;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class PorPermanenciaDTO {
    Long id;
    String nome;
    String tempoP;
}