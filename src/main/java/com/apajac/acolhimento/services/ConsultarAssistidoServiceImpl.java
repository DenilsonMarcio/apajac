package com.apajac.acolhimento.services;

import com.apajac.acolhimento.domain.entities.AssistidoEntity;
import com.apajac.acolhimento.exceptions.NotFoundException;
import com.apajac.acolhimento.repositories.AssistidoRepository;
import com.apajac.acolhimento.services.interfaces.ConsultarAssistidoService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ConsultarAssistidoServiceImpl implements ConsultarAssistidoService {

    private final AssistidoRepository assistidoRepository;
    @Override
    public Page<AssistidoEntity> listarAssistidos(Pageable pageable) {
        return assistidoRepository.findAll(pageable);
    }

    @Override
    public AssistidoEntity buscarAssistidoPorId(Long id) {
        Optional<AssistidoEntity> assistidoOpt = assistidoRepository.findById(id);
        if (assistidoOpt.isEmpty()){
            throw new NotFoundException("Assistido não encontrado.");
        }
        return assistidoOpt.get();
    }

    @Override
    public Page<AssistidoEntity> buscarAssistidosPorNome(String nome, Pageable pageable) {
        return assistidoRepository.findAllByNomeContainingIgnoreCase(nome, pageable);
    }
}
