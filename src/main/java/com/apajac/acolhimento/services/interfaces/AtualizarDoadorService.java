package com.apajac.acolhimento.services.interfaces;

import com.apajac.acolhimento.domain.dtos.DoadorDTO;

public interface AtualizarDoadorService {
    void updateDoador(Long ID, DoadorDTO doadorDTO);
}
