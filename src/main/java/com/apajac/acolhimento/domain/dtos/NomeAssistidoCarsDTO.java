package com.apajac.acolhimento.domain.dtos;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class NomeAssistidoCarsDTO {
    private String nomeAssistido;
    private List<CarsDataPontuacaoDTO> cars;
}
