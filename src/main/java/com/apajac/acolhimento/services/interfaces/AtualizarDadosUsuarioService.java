package com.apajac.acolhimento.services.interfaces;

import com.apajac.acolhimento.domain.dtos.UsuarioDTO;

public interface AtualizarDadosUsuarioService {
    void updateDadosUsuario(Long id, UsuarioDTO usuarioDTO);
}
