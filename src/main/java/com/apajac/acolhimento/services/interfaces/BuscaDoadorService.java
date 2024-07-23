package com.apajac.acolhimento.services.interfaces;

import com.apajac.acolhimento.domain.entities.DoadorEntity;


public interface BuscaDoadorService {
    DoadorEntity buscarDoadorPorId(Long id);
}
