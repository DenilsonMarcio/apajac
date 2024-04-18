package br.com.apajac.acolhimento.services.interfaces;

import br.com.apajac.acolhimento.domain.dtos.ComposicaoFamiliarDTO;
import br.com.apajac.acolhimento.domain.entities.ComposicaoFamiliarEntity;

import java.util.List;

public interface ComposicaoFamiliarService {

    ComposicaoFamiliarEntity cadastrarComposicaoFamiliar(ComposicaoFamiliarDTO composicaoFamiliar);


}
