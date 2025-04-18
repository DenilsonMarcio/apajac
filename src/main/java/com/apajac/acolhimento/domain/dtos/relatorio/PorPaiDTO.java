package com.apajac.acolhimento.domain.dtos.relatorio;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class PorPaiDTO {
    Long id;
    String nome;
    Boolean pai;
}
