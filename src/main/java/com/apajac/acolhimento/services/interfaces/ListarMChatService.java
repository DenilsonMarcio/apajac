package com.apajac.acolhimento.services.interfaces;

import com.apajac.acolhimento.domain.dtos.DetalhesMChatAssistidoDTO;
import com.apajac.acolhimento.domain.dtos.NomeAssistidoMChatDTO;

public interface ListarMChatService {
    NomeAssistidoMChatDTO listarMChatPorAssistido(Long id);
    DetalhesMChatAssistidoDTO detalhesMChatPorAssistidoEData(Long id);
}
