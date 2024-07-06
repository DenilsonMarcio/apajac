package com.apajac.acolhimento.services.interfaces;

import com.apajac.acolhimento.domain.dtos.DoadorDTO;

public interface DoadorService {
    void persistirDoador(DoadorDTO doadorDTO);
}
