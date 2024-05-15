package com.apajac.acolhimento.services;

import com.apajac.acolhimento.domain.dtos.UsuarioDTO;
import com.apajac.acolhimento.domain.entities.UsuarioEntity;
import com.apajac.acolhimento.exceptions.BusinessException;
import com.apajac.acolhimento.exceptions.NotFoundException;
import com.apajac.acolhimento.repositories.UsuarioRepository;
import com.apajac.acolhimento.services.interfaces.UsuarioService;
import com.apajac.acolhimento.utils.ExtrairMessageErroUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository repository;
    @Override
    public void cadastrar(UsuarioDTO usuarioDTO) {
        try {

            validDTO(usuarioDTO);

            if (nonNull(usuarioDTO.getId())){
                UsuarioEntity usuarioEntity = buscarUsuarioPorId(usuarioDTO.getId());
                usuarioEntity.setNome(usuarioDTO.getNome());
                usuarioEntity.setRoles(usuarioDTO.getRoles());
                usuarioEntity.setPassword(usuarioDTO.getPassword() != null ? usuarioDTO.getPassword() : usuarioEntity.getPassword());
                usuarioEntity.setLogin(usuarioDTO.getLogin());

                repository.save(usuarioEntity);
            } else {
                UsuarioEntity entity = new UsuarioEntity();
                entity.setNome(usuarioDTO.getNome());
                entity.setRoles(usuarioDTO.getRoles());
                entity.setPassword(usuarioDTO.getPassword());
                entity.setLogin(usuarioDTO.getLogin());

                repository.save(entity);
            }

        } catch (DataIntegrityViolationException e){
            throw new BusinessException(ExtrairMessageErroUsuario.extrairMensagemDeErro(e.getMessage()));
        }
    }

    private void validDTO(UsuarioDTO usuario) {
        if (isNull(usuario)){
            throw new IllegalArgumentException();
        }
    }

    @Override
    public List<UsuarioEntity> listarUsuarios() {
        return repository.findAll();
    }

    @Override
    public void remover(Long id) {
        Optional<UsuarioEntity> usuario = repository.findById(id);
        if (usuario.isEmpty()){
            throw new NotFoundException("Usuário não encontrado.");
        }
        repository.delete(usuario.get());
    }

    @Override
    public UsuarioEntity buscarUsuarioPorId(Long id) {
        Optional<UsuarioEntity> optionalUsuario = repository.findById(id);
        if (optionalUsuario.isEmpty()){
            throw new NotFoundException("Usuário não encontrado.");
        }
        return optionalUsuario.get();
    }
}
