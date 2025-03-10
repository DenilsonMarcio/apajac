package com.apajac.acolhimento.domain.dtos.relatorio;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class AtivosEInativosDTO {
    public String[] labels;
    public Integer[] values;

    public AtivosEInativosDTO(List<Boolean> labels, List<Integer> values) {
    }
}
