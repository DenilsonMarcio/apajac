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

@Service
@RequiredArgsConstructor
@Transactional
public class AtualizarDadosUsuarioServiceImpl implements AtualizarDadosUsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final AuditoriaService auditoria;

    @Override
    public void updateDadosUsuario(Long id, UsuarioDTO usuarioDTO) {

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

    private String encryptPasswordUser(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
