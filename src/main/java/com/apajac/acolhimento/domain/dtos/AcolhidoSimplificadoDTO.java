package com.apajac.acolhimento.domain.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class AcolhidoSimplificadoDTO {
    private boolean statusAcolhido;
    private String nome;
    private Integer idade;
    private String mae;
    private String pai;
}
