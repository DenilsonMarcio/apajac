package com.apajac.acolhimento.services;

import com.apajac.acolhimento.domain.entities.AssistidoEntity;
import com.apajac.acolhimento.exceptions.NotFoundException;
import com.apajac.acolhimento.repositories.AssistidoRepository;
import com.apajac.acolhimento.services.interfaces.AtualizarStatusAssistidoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AtualizarStatusAssistidoServiceImpl implements AtualizarStatusAssistidoService {

    private final AssistidoRepository assistidoRepository;
    @Override
    public void updateStatusAssistido(Long id, Long id_responsavel) {
        Optional<AssistidoEntity> assistidoOpt = assistidoRepository.findById(id);
        if (assistidoOpt.isEmpty()){
            throw new NotFoundException("Assistido não encontrado.");
        }
        AssistidoEntity assistidoEntity = assistidoOpt.get();
        assistidoRepository.save(atualizaStatusAssistido(assistidoEntity));

        //TODO UTILIZAR ID_RESPONSAVEL PARA TABELA DE HISTORICO
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
