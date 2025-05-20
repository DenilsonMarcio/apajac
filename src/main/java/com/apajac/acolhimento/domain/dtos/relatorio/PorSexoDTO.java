package com.apajac.acolhimento.domain.dtos.relatorio;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class PorSexoDTO {
    public Character[] labels;
    public Integer[] values;

    public PorSexoDTO(List<Character> labels, List<Integer> values) {
    }
}
