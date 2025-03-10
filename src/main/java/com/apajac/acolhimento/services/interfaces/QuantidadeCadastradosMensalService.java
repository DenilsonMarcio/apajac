package com.apajac.acolhimento.services.interfaces;

import com.apajac.acolhimento.domain.dtos.relatorio.CadastroMensalDTO;

import java.util.List;

public interface QuantidadeCadastradosMensalService {
    List<CadastroMensalDTO> cadastradosMensal();
}
