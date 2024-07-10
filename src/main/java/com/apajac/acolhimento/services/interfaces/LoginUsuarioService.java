package com.apajac.acolhimento.services.interfaces;

import com.apajac.acolhimento.domain.dtos.LoginDTO;
import com.apajac.acolhimento.domain.dtos.UsuarioLogadoDTO;

public interface LoginUsuarioService {
    UsuarioLogadoDTO login(LoginDTO loginDTO);
}