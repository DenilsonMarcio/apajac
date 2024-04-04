package br.com.apajac.acolhimento.services.interfaces;

import br.com.apajac.acolhimento.domain.dtos.MaeDTO;
import br.com.apajac.acolhimento.domain.entities.MaeEntity;

import java.util.List;


public interface MaeService {
    void cadastrarMae(MaeDTO maeDTO);

    List<MaeEntity> listarMae();

    MaeDTO buscarMaePorNome(String nome);

}
