package com.apajac.acolhimento.services.interfaces;

import com.apajac.acolhimento.domain.dtos.DoadorDTO;
import org.springframework.validation.annotation.Validated;

@Validated
public interface EditarDoadorService {

    void editarDoador(DoadorDTO doadorDTO);
}