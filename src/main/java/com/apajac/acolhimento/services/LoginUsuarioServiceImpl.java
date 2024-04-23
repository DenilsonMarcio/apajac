package com.apajac.acolhimento.services;

import com.apajac.acolhimento.domain.dtos.LoginDTO;
import com.apajac.acolhimento.domain.dtos.UsuarioLogadoDTO;
import com.apajac.acolhimento.domain.entities.UsuarioEntity;
import com.apajac.acolhimento.exceptions.PermissionException;
import com.apajac.acolhimento.exceptions.UnauthorizedException;
import com.apajac.acolhimento.repositories.UsuarioRepository;
import com.apajac.acolhimento.services.interfaces.LoginUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginUsuarioServiceImpl implements LoginUsuarioService {

    private final UsuarioRepository usuarioRepository;
    @Override
    public UsuarioLogadoDTO login(LoginDTO loginDTO) {

        String login = loginDTO.getLogin();
        String password = loginDTO.getPassword();

        Optional<UsuarioEntity> optionalUsuario = usuarioRepository.getUsuarioByLoginAndPassword(login, password);

        return validUsuarioLogado(optionalUsuario);
    }

    private UsuarioLogadoDTO validUsuarioLogado(Optional<UsuarioEntity> optionalUsuario) {
        if (optionalUsuario.isEmpty()){
            throw new UnauthorizedException("Usuário informado não autorizado.");
        }

        UsuarioEntity usuarioEntity = optionalUsuario.get();

        if (!usuarioEntity.getStatus()){
            throw new PermissionException("Usuário informado esta inativo.");
        }

        return UsuarioLogadoDTO.builder()
                .nome(usuarioEntity.getNome())
                .role(usuarioEntity.getRole())
                .build();
    }
}
