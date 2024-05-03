package com.apajac.acolhimento.domain.dtos;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ListaAcolhidoDTO {
    private Boolean isLastPage;
    private List<AcolhidoSimplificadoDTO> acolhidos;
}
