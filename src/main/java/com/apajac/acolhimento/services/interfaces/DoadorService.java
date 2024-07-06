package com.apajac.acolhimento.services.interfaces;

import com.apajac.acolhimento.domain.entities.DoadorEntity;

public interface DoadorService {
    DoadorEntity buscarDoadorPorId(Long id);
    void removerDoador(Long id);
}
