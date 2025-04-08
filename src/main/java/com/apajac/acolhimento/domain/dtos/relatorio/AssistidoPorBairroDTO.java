package com.apajac.acolhimento.domain.dtos.relatorio;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class AssistidoPorBairroDTO {
    String bairro;
    Long total_assistidos;
    BigDecimal media_idade;
}
