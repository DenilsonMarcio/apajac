package com.apajac.acolhimento.services.interfaces;

import com.apajac.acolhimento.domain.entities.AssistidoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ConsultarAssistidoService {
    Page<AssistidoEntity> listarAssistidos(Pageable pageable);

    AssistidoEntity buscarAssistidoPorId(Long id);

    Page<AssistidoEntity> buscarAssistidosPorNome(String nome, Pageable pageable);
}
