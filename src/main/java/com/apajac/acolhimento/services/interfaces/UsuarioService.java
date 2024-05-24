package com.apajac.acolhimento.services.interfaces;

import com.apajac.acolhimento.domain.dtos.UsuarioDTO;
import com.apajac.acolhimento.domain.entities.UsuarioEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UsuarioService {
    void cadastrar(UsuarioDTO usuario);
    Page<UsuarioEntity> listarUsuarios(Pageable pageable);
    void remover(Long id);
    UsuarioEntity buscarUsuarioPorId(Long id);
}
