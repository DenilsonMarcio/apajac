package br.com.apajac.acolhimento.services.interfaces;

import br.com.apajac.acolhimento.domain.dtos.ContratoAcolhidoDTO;
import br.com.apajac.acolhimento.domain.entities.AcolhidoEntity;

import java.util.List;

public interface AcolhidoService {

   void cadastrarAcolhido(ContratoAcolhidoDTO contrato);

}
