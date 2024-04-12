package br.com.apajac.acolhimento.services.interfaces;

import br.com.apajac.acolhimento.domain.dtos.ContratoAcolhidoDTO;
import br.com.apajac.acolhimento.domain.entities.ContratoAcolhidoEntity;

public interface ContratoAcolhidoService {

    ContratoAcolhidoEntity cadastrarContrato(ContratoAcolhidoDTO contratoAcolhido);
}
