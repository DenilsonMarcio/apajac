package br.com.apajac.acolhimento.services.interfaces;

import br.com.apajac.acolhimento.domain.dtos.EnderecoDTO;
import br.com.apajac.acolhimento.domain.entities.EnderecoEntity;

public interface EnderecoService {
    
    EnderecoEntity cadastrarEndereco(EnderecoDTO endereco);

}

