package br.com.apajac.acolhimento.services.interfaces;

import br.com.apajac.acolhimento.domain.dtos.AcolhidoDTO;
import br.com.apajac.acolhimento.domain.entities.AcolhidoEntity;

import java.util.List;

public interface AcolhidoService {

    AcolhidoEntity cadastrarAcolhido(AcolhidoDTO acolhido);
    List<AcolhidoEntity> listarAcolhidos();

}
