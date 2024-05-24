package com.apajac.acolhimento.services;

import com.apajac.acolhimento.domain.entities.AssistidoEntity;
import com.apajac.acolhimento.domain.enums.AuditoriaEnum;
import com.apajac.acolhimento.exceptions.NotFoundException;
import com.apajac.acolhimento.repositories.AssistidoRepository;
import com.apajac.acolhimento.services.interfaces.AtualizarStatusAssistidoService;
import com.apajac.acolhimento.services.interfaces.AuditoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AtualizarStatusAssistidoServiceImpl implements AtualizarStatusAssistidoService {

    private final AssistidoRepository assistidoRepository;

    private final AuditoriaService auditoria;
    @Override
    public void updateStatusAssistido(Long id, Long id_responsavel) {
        Optional<AssistidoEntity> assistidoOpt = assistidoRepository.findById(id);
        if (assistidoOpt.isEmpty()){
            throw new NotFoundException("Assistido não encontrado.");
        }
        AssistidoEntity assistidoEntity = assistidoOpt.get();

        auditar(id.toString(), id_responsavel);

        assistidoRepository.save(atualizaStatusAssistido(assistidoEntity));
    }

    private void auditar(String body, Long idResponsavel) {
        auditoria.inserirDadosDeAuditoria(
                idResponsavel,
                AuditoriaEnum.UPDATED.getValues(),
                AtualizarStatusAssistidoService.class.getSimpleName(),
                body);
    }

    private AssistidoEntity atualizaStatusAssistido(AssistidoEntity assistidoEntity) {
        if (assistidoEntity.isStatusAssistido()) {
            assistidoEntity.setStatusAssistido(Boolean.FALSE);
        } else {
            assistidoEntity.setStatusAssistido(Boolean.TRUE);
        }
        return assistidoEntity;
    }
}
