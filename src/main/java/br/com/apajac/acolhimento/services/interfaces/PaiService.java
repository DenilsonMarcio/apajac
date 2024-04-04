package br.com.apajac.acolhimento.services.interfaces;

import br.com.apajac.acolhimento.domain.dtos.PaiDTO;
import br.com.apajac.acolhimento.domain.entities.PaiEntity;

import java.util.List;

public interface PaiService {

    void cadastrarPai(PaiDTO paiDTO);

    List<PaiEntity> listarPai();

    PaiDTO buscarMaePorNome(String nome);


}
