package com.apajac.acolhimento.services.interfaces;

import com.apajac.acolhimento.domain.dtos.DoadorDTO;
import com.apajac.acolhimento.domain.entities.DoadorEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DoadorService {
    void persistirDoador(DoadorDTO doadorDTO);
    Page<DoadorEntity> listarDoadores(Pageable pageable);
    DoadorEntity buscarDoadorPorId(Long id);
    void removerDoador(Long id);
}
