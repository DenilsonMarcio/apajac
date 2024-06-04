package com.apajac.acolhimento.services;

import com.apajac.acolhimento.domain.dtos.LoginDTO;
import com.apajac.acolhimento.domain.dtos.UsuarioLogadoDTO;
import com.apajac.acolhimento.domain.entities.UsuarioEntity;
import com.apajac.acolhimento.exceptions.NotFoundException;
import com.apajac.acolhimento.exceptions.PermissionException;
import com.apajac.acolhimento.exceptions.UnauthorizedException;
import com.apajac.acolhimento.repositories.UsuarioRepository;
import com.apajac.acolhimento.services.interfaces.LoginUsuarioService;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
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

        Optional<UsuarioEntity> optionalUsuario = usuarioRepository.getUsuarioByLogin(login);

        return validUsuarioLogado(optionalUsuario, password);
    }

    private UsuarioLogadoDTO validUsuarioLogado(Optional<UsuarioEntity> optionalUsuario, String ps) {
        if (optionalUsuario.isEmpty()){
            throw new NotFoundException("Usuário não cadastrado.");
        }
        UsuarioEntity usuarioEntity = optionalUsuario.get();
        if (!usuarioEntity.isStatus()){
            throw new PermissionException("Usuário informado com status inativo.");
        }

        boolean isValid = BCrypt.checkpw(ps, usuarioEntity.getPassword());

        if (!isValid) {
            throw new UnauthorizedException("Acesso Negado.");
        }
        return UsuarioLogadoDTO.builder()
                .id(usuarioEntity.getId())
                .nome(usuarioEntity.getNome())
                .login(usuarioEntity.getLogin())
                .roles(usuarioEntity.getRoles())
                .build();
    }
}
