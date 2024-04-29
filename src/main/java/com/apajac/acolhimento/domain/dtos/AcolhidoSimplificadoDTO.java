package com.apajac.acolhimento.domain.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class AcolhidoSimplificadoDTO {
    private Long id;
    private String nome;
    private Integer idade;
    private String responsavel;
    private boolean statusAcolhido;
}
