package com.apajac.acolhimento.services.interfaces;

import com.apajac.acolhimento.domain.dtos.DoadorDTO;
import org.springframework.validation.annotation.Validated;

@Validated
public interface PersistirDoadorService {

    void persistirDoador(DoadorDTO doadorDTO);
}

