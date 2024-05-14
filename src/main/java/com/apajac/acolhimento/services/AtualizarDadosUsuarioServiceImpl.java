package com.apajac.acolhimento.services;

import com.apajac.acolhimento.domain.dtos.UsuarioDTO;
import com.apajac.acolhimento.domain.entities.UsuarioEntity;
import com.apajac.acolhimento.exceptions.NotFoundException;
import com.apajac.acolhimento.repositories.UsuarioRepository;
import com.apajac.acolhimento.services.interfaces.AtualizarDadosUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AtualizarDadosUsuarioServiceImpl implements AtualizarDadosUsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public void updateDadosUsuario(Long id, UsuarioDTO usuarioDTO) {

        Optional<UsuarioEntity> optionalUsuarioEntity = usuarioRepository.findById(id);
        if (optionalUsuarioEntity.isEmpty()){
            throw new NotFoundException("Usuário não encontrado.");
        }

        UsuarioEntity usuarioEntity = optionalUsuarioEntity.get();
        usuarioEntity.setId(id);
        usuarioEntity.setNome(usuarioDTO.getNome());
        usuarioEntity.setLogin(usuarioDTO.getLogin());
        usuarioEntity.setRoles(usuarioDTO.getRoles());

        if (!usuarioDTO.getPassword().isEmpty()){
            usuarioEntity.setPassword(usuarioDTO.getPassword());
        }

        usuarioRepository.save(usuarioEntity);

    }
}
