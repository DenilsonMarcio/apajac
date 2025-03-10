package com.apajac.acolhimento.domain.dtos.relatorio;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class FaixaEtariaDTO {
    public String[] labels;
    public Integer[] values;

    public FaixaEtariaDTO(List<Character> labels, List<Integer> values) {
    }
}
