package br.com.apajac.acolhimento.services.interfaces;

import br.com.apajac.acolhimento.domain.dtos.UsuarioDTO;
import br.com.apajac.acolhimento.domain.entities.UsuarioEntity;

public interface UsuarioService {
    void cadastrar(UsuarioDTO usuario);
}
