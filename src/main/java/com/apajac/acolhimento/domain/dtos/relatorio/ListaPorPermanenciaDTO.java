package com.apajac.acolhimento.domain.dtos.relatorio;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ListaPorPermanenciaDTO {
    private Boolean isLastPage;
    private List<PorPermanenciaDTO> ListaPorPermanencia;
}
