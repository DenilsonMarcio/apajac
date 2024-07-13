package com.apajac.acolhimento.services.interfaces;

import com.apajac.acolhimento.domain.entities.DoadorEntity;


public interface ConsultarDoadorService {
    DoadorEntity buscarDoadorPorId(Long id);


}
