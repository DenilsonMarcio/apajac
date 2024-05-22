package com.apajac.acolhimento.domain.dtos;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ListaAssistidoDTO {
    private Boolean isLastPage;
    private List<AssistidoSimplificadoDTO> assistidos;
}
