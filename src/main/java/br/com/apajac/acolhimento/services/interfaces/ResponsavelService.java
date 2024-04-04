package br.com.apajac.acolhimento.services.interfaces;

import br.com.apajac.acolhimento.domain.dtos.ResponsavelDTO;
import br.com.apajac.acolhimento.domain.entities.ResponsavelEntity;

import java.util.List;

public interface ResponsavelService {

    void cadastrarResponsavel(ResponsavelDTO responsavelDTO);

    List<ResponsavelEntity> listarResponsavel();

    ResponsavelDTO buscarResponsavelPorNome(String nome);

}
