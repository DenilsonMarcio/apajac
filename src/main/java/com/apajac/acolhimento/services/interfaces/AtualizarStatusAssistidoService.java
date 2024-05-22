package com.apajac.acolhimento.services.interfaces;

public interface AtualizarStatusAssistidoService {
    void updateStatusAssistido(Long id, Boolean status, Long id_responsavel);
}
