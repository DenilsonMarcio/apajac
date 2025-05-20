package com.apajac.acolhimento.domain.dtos;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class NomeAssistidoMChatDTO {
    private String nomeAssistido;
    private List<MChatDataPeaDTO> mchat;
}
