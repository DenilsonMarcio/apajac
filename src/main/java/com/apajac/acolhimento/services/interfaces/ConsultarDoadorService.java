package com.apajac.acolhimento.services.interfaces;

import com.apajac.acolhimento.domain.dtos.DoadorDTO;
import com.apajac.acolhimento.domain.entities.DoadorEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface ConsultarDoadorService {

    Page<DoadorEntity> listarDoadoresPage(Pageable pageable);


}
