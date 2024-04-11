package com.apajac.acolhimento.services;

import com.apajac.acolhimento.domain.entities.AcolhidoEntity;
import com.apajac.acolhimento.exceptions.NotFoundException;
import com.apajac.acolhimento.repositories.AcolhidoRepository;
import com.apajac.acolhimento.services.interfaces.AtualizarStatusAcolhidoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AtualizarStatusAcolhidoServiceImpl implements AtualizarStatusAcolhidoService {

    private final AcolhidoRepository acolhidoRepository;
    @Override
    public void updateStatusAcolhido(Long id, Boolean status) {
        Optional<AcolhidoEntity> acolhidoOpt = acolhidoRepository.findById(id);
        if (acolhidoOpt.isEmpty()){
            throw new NotFoundException("Acolhido não encontrado.");
        }
        AcolhidoEntity acolhidoEntity = acolhidoOpt.get();
        acolhidoEntity.setStatusAcolhido(status);
        acolhidoRepository.save(acolhidoEntity);
    }
}
