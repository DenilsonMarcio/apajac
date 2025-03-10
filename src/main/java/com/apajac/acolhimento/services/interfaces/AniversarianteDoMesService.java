package com.apajac.acolhimento.services.interfaces;

import com.apajac.acolhimento.domain.dtos.relatorio.AniversarianteDoMesDTO;

import java.util.List;

public interface AniversarianteDoMesService {
    List<AniversarianteDoMesDTO> aniversariantesDoMes(Integer mes);
}
