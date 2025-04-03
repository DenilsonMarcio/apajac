package com.apajac.acolhimento.domain.dtos.relatorio;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CadastroMensalDTO {
    public String[] labels;
    public Long[] values;

    public CadastroMensalDTO(List<String> labels, List<Long> values) {
    }
}
