package com.apajac.acolhimento.services.interfaces;

import com.apajac.acolhimento.domain.dtos.UsuarioDTO;
import com.apajac.acolhimento.domain.entities.UsuarioEntity;

import java.util.List;

public interface UsuarioService {
    void cadastrar(UsuarioDTO usuario);
    List<UsuarioEntity> listarUsuarios();
    void remover(Long id);
    UsuarioEntity buscarUsuarioPorId(Long id);
}
