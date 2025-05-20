package com.apajac.acolhimento.domain.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class RespostaCarsDTO {
    private Integer pergunta;
    private Double resposta;
    private String observacoes;
}
