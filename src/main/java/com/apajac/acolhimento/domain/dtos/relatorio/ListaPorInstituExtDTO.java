package com.apajac.acolhimento.domain.dtos.relatorio;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ListaPorInstituExtDTO {
    private Boolean isLastPage;
    private List<PorInstituExtDTO> ListaPorInstituExt;
}
