package com.apajac.acolhimento.domain.dtos.relatorio;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class PorIdadeDTO {
    public String[] labels;
    public Integer[] values;

    public PorIdadeDTO(List<Character> labels, List<Integer> values) {
    }
}
