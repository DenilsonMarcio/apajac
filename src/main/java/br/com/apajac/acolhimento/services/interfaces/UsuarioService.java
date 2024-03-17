package br.com.apajac.acolhimento.services.interfaces;

import br.com.apajac.acolhimento.domain.dtos.UsuarioDTO;
import br.com.apajac.acolhimento.domain.entities.UsuarioEntity;

import java.util.List;

public interface UsuarioService {
    void cadastrar(UsuarioDTO usuario);
    List<UsuarioEntity> listarUsuarios();
}
