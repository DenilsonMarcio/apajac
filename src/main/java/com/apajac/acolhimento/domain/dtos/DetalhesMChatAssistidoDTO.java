package com.apajac.acolhimento.domain.dtos;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class DetalhesMChatAssistidoDTO {
    private String nomeAssistido;
    private LocalDate data;
    private Boolean pea;
    private List<RespostaMChatDTO> detalhes;
}
