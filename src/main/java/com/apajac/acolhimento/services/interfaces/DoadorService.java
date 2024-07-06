package com.apajac.acolhimento.services.interfaces;

import com.apajac.acolhimento.domain.entities.DoadorEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DoadorService {
    Page<DoadorEntity> listarDoadores(Pageable pageable);
}
