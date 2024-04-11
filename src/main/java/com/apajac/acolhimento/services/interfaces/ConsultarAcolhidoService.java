package com.apajac.acolhimento.services.interfaces;

import com.apajac.acolhimento.domain.entities.AcolhidoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ConsultarAcolhidoService {
    Page<AcolhidoEntity> listarAcolhidos(Pageable pageable);

    AcolhidoEntity buscarAcolhidoPorId(Long id);

    Page<AcolhidoEntity> buscarAcolhidosPorNome(String nome, Pageable pageable);
}
