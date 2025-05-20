package com.apajac.acolhimento.domain.dtos;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CarsDataPontuacaoDTO {
    private Long id;
    private LocalDate data;
    private Double pontuacao;
}
