package com.apajac.acolhimento.services;

import com.apajac.acolhimento.domain.dtos.UsuarioDTO;
import com.apajac.acolhimento.domain.entities.UsuarioEntity;
import com.apajac.acolhimento.domain.enums.AuditoriaEnum;
import com.apajac.acolhimento.exceptions.BusinessException;
import com.apajac.acolhimento.exceptions.NotFoundException;
import com.apajac.acolhimento.repositories.UsuarioRepository;
import com.apajac.acolhimento.services.interfaces.AuditoriaService;
import com.apajac.acolhimento.services.interfaces.UsuarioService;
import com.apajac.acolhimento.utils.ExtrairMessageErroUsuario;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository repository;

    private final AuditoriaService auditoria;
    @Override
    public void cadastrar(UsuarioDTO usuarioDTO) {
        try {
            validDTO(usuarioDTO);

            if (nonNull(usuarioDTO.getId())) {
                UsuarioEntity usuarioEntity = buscarUsuarioPorId(usuarioDTO.getId());
                usuarioEntity.setNome(usuarioDTO.getNome());
                usuarioEntity.setRoles(usuarioDTO.getRoles());
                usuarioEntity.setPassword(usuarioDTO.getPassword() != null
                        ? encryptPasswordUser(usuarioDTO.getPassword())
                        : usuarioEntity.getPassword());
                usuarioEntity.setLogin(usuarioDTO.getLogin());

                auditar(usuarioEntity.toString(), usuarioDTO.getIdResponsavelPeloCadastro(), AuditoriaEnum.UPDATED.getValues());

                repository.save(usuarioEntity);

            } else {
                Optional<UsuarioEntity> existingUser = repository.findByLogin(usuarioDTO.getLogin());
                if (existingUser.isPresent()) {
                    throw new BusinessException("Login já existe.");
                }

                UsuarioEntity entity = new UsuarioEntity();
                entity.setNome(usuarioDTO.getNome());
                entity.setRoles(usuarioDTO.getRoles());
                entity.setPassword(encryptPasswordUser(usuarioDTO.getPassword()));
                entity.setLogin(usuarioDTO.getLogin());

                repository.save(entity);

                auditar(entity.toString(), usuarioDTO.getIdResponsavelPeloCadastro(), AuditoriaEnum.CREATED.getValues());
            }
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException(ExtrairMessageErroUsuario.extrairMensagemDeErro(e.getMessage()));
        }
    }

    private void auditar(String body, Long idResponsavel, String tipo) {
        auditoria.inserirDadosDeAuditoria(
                idResponsavel,
                tipo,
                UsuarioService.class.getSimpleName(),
                body);
    }

    private String encryptPasswordUser(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    private void validDTO(UsuarioDTO usuario) {
        if (isNull(usuario)) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Page<UsuarioEntity> listarUsuarios(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public void remover(Long id) {
        Optional<UsuarioEntity> usuario = repository.findById(id);
        if (usuario.isEmpty()) {
            throw new NotFoundException("Usuário não encontrado.");
        }
        repository.delete(usuario.get());
    }

    @Override
    public UsuarioEntity buscarUsuarioPorId(Long id) {
        Optional<UsuarioEntity> optionalUsuario = repository.findById(id);
        if (optionalUsuario.isEmpty()) {
            throw new NotFoundException("Usuário não encontrado.");
        }
        return optionalUsuario.get();
    }
}
