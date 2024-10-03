package com.apajac.acolhimento.services;

import com.apajac.acolhimento.domain.dtos.UsuarioDTO;
import com.apajac.acolhimento.domain.entities.UsuarioEntity;
import com.apajac.acolhimento.domain.enums.AuditoriaEnum;
import com.apajac.acolhimento.exceptions.BusinessException;
import com.apajac.acolhimento.exceptions.NotFoundException;
import com.apajac.acolhimento.repositories.UsuarioRepository;
import com.apajac.acolhimento.services.interfaces.AtualizarDadosUsuarioService;
import com.apajac.acolhimento.services.interfaces.AuditoriaService;
import com.apajac.acolhimento.services.interfaces.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
@Transactional
public class AtualizarDadosUsuarioServiceImpl implements AtualizarDadosUsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final AuditoriaService auditoria;

    @Override
    public void updateDadosUsuario(Long id, UsuarioDTO usuarioDTO) {

        validDTO(usuarioDTO);

        Optional<UsuarioEntity> optionalUsuarioEntity = usuarioRepository.findById(id);
        if (optionalUsuarioEntity.isEmpty()){
            throw new NotFoundException("Usuário não encontrado.");
        }

        UsuarioEntity usuarioEntity = optionalUsuarioEntity.get();
        validaUsuarioRoot(usuarioEntity);

        usuarioEntity.setId(id);
        usuarioEntity.setNome(usuarioDTO.getNome());
        usuarioEntity.setLogin(usuarioDTO.getLogin());
        usuarioEntity.setRoles(usuarioDTO.getRoles());

        if (!usuarioDTO.getPassword().isEmpty()){
            usuarioEntity.setPassword(encryptPasswordUser(usuarioDTO.getPassword()));
        }

        auditar(usuarioEntity.toString(), usuarioDTO.getIdResponsavelPeloCadastro());

        usuarioRepository.save(usuarioEntity);
    }

    private void validaUsuarioRoot(UsuarioEntity usuarioEntity) {
        boolean root = usuarioEntity.getRoles().contains("root");
        if (root){
            throw new BusinessException("Usuário ROOT não pode ser alterado.");
        }
    }

    private void auditar(String body, Long idResponsavel) {
        auditoria.inserirDadosDeAuditoria(
                idResponsavel,
                AuditoriaEnum.UPDATED.getValues(),
                UsuarioService.class.getSimpleName(),
                body);
    }
    private void validDTO(UsuarioDTO usuario) {
        if (isNull(usuario)) {
            throw new IllegalArgumentException("O DTO do usuário não pode ser nulo.");
        } else if (isNull(usuario.getNome())||usuario.getNome().isBlank()) {
            throw new IllegalArgumentException("O Nome do usuário não pode ser nulo/vazio.");
        } else if (isNull(usuario.getRoles())||usuario.getRoles().isEmpty()) {
            throw new IllegalArgumentException("Os Papéis do usuário não podem ser nulos/vazios.");
        } else if (isNull(usuario.getLogin())||usuario.getLogin().isBlank()) {
            throw new IllegalArgumentException("O Login do usuário não pode ser nulo/vazio.");
        }else if (!usuario.getPassword().isBlank()&&usuario.getPassword().length() < 6) {
            throw new IllegalArgumentException("A Senha do usuário não pode ser menor que 6 dígitos.");
        }else if (isNull(usuario.getIdResponsavelPeloCadastro())) {
            throw new IllegalArgumentException("O ID de Responsável não pode ser nulo.");
        }
    }

    private String encryptPasswordUser(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
