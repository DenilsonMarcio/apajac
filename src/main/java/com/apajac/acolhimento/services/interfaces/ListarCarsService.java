package com.apajac.acolhimento.services.interfaces;

import com.apajac.acolhimento.domain.dtos.DetalhesCarsAssistidoDTO;
import com.apajac.acolhimento.domain.dtos.NomeAssistidoCarsDTO;

public interface ListarCarsService {
    NomeAssistidoCarsDTO listarCarsPorAssistido(Long id);
    DetalhesCarsAssistidoDTO detalhesCarsPorAssistidoEData(Long id);
}
