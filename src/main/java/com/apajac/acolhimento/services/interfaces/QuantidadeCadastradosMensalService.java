package com.apajac.acolhimento.services.interfaces;

import com.apajac.acolhimento.domain.dtos.relatorio.CadastroMensalDTO;

public interface QuantidadeCadastradosMensalService {
    CadastroMensalDTO cadastradosMensal(Integer codAno);
}
