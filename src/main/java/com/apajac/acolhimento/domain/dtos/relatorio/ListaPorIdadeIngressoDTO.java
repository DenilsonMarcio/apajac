package com.apajac.acolhimento.domain.dtos.relatorio;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ListaPorIdadeIngressoDTO {
    private Boolean isLastPage;
    private List<PorIdadeIngressoDTO> ListaPorIdadeIngresso;
}
