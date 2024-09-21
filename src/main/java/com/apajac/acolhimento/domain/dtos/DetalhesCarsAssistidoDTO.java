package com.apajac.acolhimento.domain.dtos;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class DetalhesCarsAssistidoDTO {
    private LocalDate data;
    private Double pontuacao;
    private List<RespostaCarsDTO> detalhes;
}
