package com.apajac.acolhimento.utils;

import com.apajac.acolhimento.domain.entities.AssistidoEntity;
import com.apajac.acolhimento.exceptions.NotFoundException;
import com.apajac.acolhimento.repositories.AssistidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AssistidoUtils {

    private final AssistidoRepository assistidoRepository;

    public AssistidoEntity verificaAssistido(Long id) {
        Optional<AssistidoEntity> optionalAssistido = assistidoRepository.findById(id);
        if (optionalAssistido.isEmpty()){
            throw new NotFoundException("Assistido não encontrado!");
        }
        return optionalAssistido.get();
    }
}
