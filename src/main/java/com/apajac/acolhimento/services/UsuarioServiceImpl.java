package com.apajac.acolhimento.services;

import com.apajac.acolhimento.domain.dtos.UsuarioDTO;
import com.apajac.acolhimento.domain.entities.UsuarioEntity;
import com.apajac.acolhimento.repositories.UsuarioRepository;
import com.apajac.acolhimento.services.interfaces.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository repository;
    @Override
    public void cadastrar(UsuarioDTO usuario) {

        UsuarioEntity entity = new UsuarioEntity();
        entity.setNome(usuario.getNome());
        entity.setRole(usuario.getRole());
        entity.setPassword(usuario.getPassword());
        entity.setLogin(usuario.getLogin());

        repository.save(entity);
    }

    @Override
    public List<UsuarioEntity> listarUsuarios() {
        return repository.findAll();
    }
}
