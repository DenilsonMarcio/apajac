package br.com.apajac.acolhimento.services.interfaces;

import br.com.apajac.acolhimento.domain.dtos.ResponsavelDTO;
import br.com.apajac.acolhimento.domain.entities.ResponsavelEntity;

public interface ResponsavelService {
    ResponsavelEntity cadastrarResponsavel(ResponsavelDTO responsavel);


}
