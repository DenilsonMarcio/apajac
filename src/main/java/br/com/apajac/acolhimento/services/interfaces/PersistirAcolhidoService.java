package br.com.apajac.acolhimento.services.interfaces;

import br.com.apajac.acolhimento.domain.dtos.AcolhidoDTO;
import br.com.apajac.acolhimento.domain.entities.AcolhidoEntity;

import java.util.List;

public interface PersistirAcolhidoService {
    void persistirAcolhido(AcolhidoDTO acolhidoDTO);


}
