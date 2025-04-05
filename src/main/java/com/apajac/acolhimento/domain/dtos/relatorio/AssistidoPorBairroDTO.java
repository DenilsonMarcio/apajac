package com.apajac.acolhimento.domain.dtos.relatorio;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class AssistidoPorBairroDTO {
    String bairro;
    Long total_assistidos;
}
