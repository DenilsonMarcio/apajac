package com.apajac.acolhimento.services.interfaces;

import com.apajac.acolhimento.domain.dtos.relatorio.AssistidoPorBairroDTO;
import com.apajac.acolhimento.domain.entities.AssistidoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ConsultarAssistidoService {
    Page<AssistidoEntity> listarAssistidos(Pageable pageable);

    AssistidoEntity buscarAssistidoPorId(Long id);

    Page<AssistidoEntity> buscarAssistidosPorNome(String nome, Pageable pageable);

    List<Integer> getAnosCadastros();

    List<AssistidoPorBairroDTO> totalAssistidoPorBairro(String bairro);
}
