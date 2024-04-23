package com.apajac.acolhimento.services;

import com.apajac.acolhimento.domain.entities.UsuarioEntity;
import com.apajac.acolhimento.exceptions.NotFoundException;
import com.apajac.acolhimento.repositories.UsuarioRepository;
import com.apajac.acolhimento.services.interfaces.AtualizarStatusUsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AtualizarStatusUsuarioServiceImpl implements AtualizarStatusUsuarioService {
    private final UsuarioRepository usuarioRepository;
    @Override
    public void updateStatusUsuario(Integer id, Boolean status) {
        Optional<UsuarioEntity> optionalUsuarioEntity = usuarioRepository.findById(id);
        if (optionalUsuarioEntity.isEmpty()){
            throw new NotFoundException("Usuário não encontrado.");
        }
        UsuarioEntity usuarioEntity = optionalUsuarioEntity.get();
        usuarioEntity.setStatus(status);
        usuarioRepository.save(usuarioEntity);
    }
}
