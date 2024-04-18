package br.com.apajac.acolhimento.services.interfaces;

import br.com.apajac.acolhimento.domain.dtos.ContratoAcolhidoDTO;
import br.com.apajac.acolhimento.domain.dtos.FamiliaresDTO;
import br.com.apajac.acolhimento.domain.entities.ContratoAcolhidoEntity;

import java.util.List;

public interface ContratoAcolhidoService {

    ContratoAcolhidoEntity cadastrarContrato(ContratoAcolhidoDTO contratoAcolhido);
}
