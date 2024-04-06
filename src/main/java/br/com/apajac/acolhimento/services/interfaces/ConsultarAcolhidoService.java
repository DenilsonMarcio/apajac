package br.com.apajac.acolhimento.services.interfaces;

import br.com.apajac.acolhimento.domain.entities.AcolhidoEntity;

import java.util.List;

public interface ConsultarAcolhidoService {
    List<AcolhidoEntity> listarAcolhidos();

    AcolhidoEntity buscarAcolhidoPorId(Long id);
}
