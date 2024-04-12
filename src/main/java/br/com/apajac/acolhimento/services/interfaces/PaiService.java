package br.com.apajac.acolhimento.services.interfaces;


import br.com.apajac.acolhimento.domain.dtos.PaiDTO;
import br.com.apajac.acolhimento.domain.entities.PaiEntity;

public interface PaiService {

    PaiEntity cadastrarPai(PaiDTO parente);

}

