package com.apajac.acolhimento.domain.dtos;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CarsDTO {
    private Long id;
    private Double pontuacao;
    private List<RespostaCarsDTO> cars;
}
