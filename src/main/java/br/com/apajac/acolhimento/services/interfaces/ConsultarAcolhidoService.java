package br.com.apajac.acolhimento.services.interfaces;

import br.com.apajac.acolhimento.domain.entities.AcolhidoEntity;

import java.util.List;
import java.util.Optional;

public interface ConsultarAcolhidoService {
    List<AcolhidoEntity> listarAcolhidos();

    AcolhidoEntity buscarAcolhidoPorId(Long id);
}
