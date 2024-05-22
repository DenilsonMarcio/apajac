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
    public void updateStatusAssistido(Long id, Boolean status, Long id_responsavel) {
        Optional<AssistidoEntity> acolhidoOpt = assistidoRepository.findById(id);
        if (acolhidoOpt.isEmpty()){
            throw new NotFoundException("Acolhido não encontrado.");
        }
        AssistidoEntity assistidoEntity = acolhidoOpt.get();
        assistidoEntity.setStatusAcolhido(status);
        assistidoRepository.save(assistidoEntity);
    }
}
