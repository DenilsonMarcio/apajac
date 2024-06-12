package com.apajac.acolhimento.services;

import com.apajac.acolhimento.domain.entities.UsuarioEntity;
import com.apajac.acolhimento.domain.enums.AuditoriaEnum;
import com.apajac.acolhimento.exceptions.BusinessException;
import com.apajac.acolhimento.exceptions.BusinessExceptionMessage;
import com.apajac.acolhimento.exceptions.NotFoundException;
import com.apajac.acolhimento.repositories.UsuarioRepository;
import com.apajac.acolhimento.services.interfaces.AtualizarStatusAssistidoService;
import com.apajac.acolhimento.services.interfaces.AtualizarStatusUsuarioService;
import com.apajac.acolhimento.services.interfaces.AuditoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AtualizarStatusUsuarioServiceImpl implements AtualizarStatusUsuarioService {
    private final UsuarioRepository usuarioRepository;

    private final AuditoriaService auditoria;
    @Override
    public void updateStatusUsuario(Long id, Long id_responsavel) {

        validarSeEhMesmoUsuario(id, id_responsavel);

        Optional<UsuarioEntity> optionalUsuarioEntity = usuarioRepository.findById(id);
        if (optionalUsuarioEntity.isEmpty()){
            throw new NotFoundException("Usuário não encontrado.");
        }
        UsuarioEntity usuarioEntity = optionalUsuarioEntity.get();

        validaUsuarioRoot(usuarioEntity);

        auditar(id.toString(), id_responsavel);

        usuarioRepository.save(atualizaStatusUsuario(usuarioEntity));
    }

    private void validarSeEhMesmoUsuario(Long id, Long id_responsavel) {
        boolean equals = id.equals(id_responsavel);
        if (equals){
            throw new BusinessException("Não é possível desativar o seu próprio usuário.");
        }
    }

//    private void validaUsuarioRoot(UsuarioEntity usuarioEntity) {
//        boolean root = usuarioEntity.getRoles().contains("root");
//        if (root){
//            throw new BusinessException("Usuário ROOT não pode ser desativado.");
//        }
//    }

    private void validaUsuarioRoot(UsuarioEntity usuarioEntity) {
        boolean root = usuarioEntity.getRoles().contains("root");
        if (root) {
            BusinessExceptionMessage message = new BusinessExceptionMessage("Usuário ROOT não pode ser desativado.");
            throw new BusinessException(message);
        }
    }


    private void auditar(String body, Long idResponsavel) {
        auditoria.inserirDadosDeAuditoria(
                idResponsavel,
                AuditoriaEnum.UPDATED.getValues(),
                AtualizarStatusAssistidoService.class.getSimpleName(),
                body);
    }

    private UsuarioEntity atualizaStatusUsuario(UsuarioEntity usuarioEntity) {
        if (usuarioEntity.isStatus()) {
            usuarioEntity.setStatus(Boolean.FALSE);
        } else {
            usuarioEntity.setStatus(Boolean.TRUE);
        }
        return usuarioEntity;
    }
}