package br.com.apajac.acolhimento.services.interfaces;

import br.com.apajac.acolhimento.domain.dtos.MaeDTO;
import br.com.apajac.acolhimento.domain.entities.MaeEntity;

public interface MaeService {

    MaeEntity cadastrarMae(MaeDTO parente);

}

