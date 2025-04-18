package com.apajac.acolhimento.domain.dtos.relatorio;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ListaPorPaiDTO {
    private Boolean isLastPage;
    private List<PorPaiDTO> listaPorPai;
}
