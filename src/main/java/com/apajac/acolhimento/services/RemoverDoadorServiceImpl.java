package com.apajac.acolhimento.services;

import com.apajac.acolhimento.domain.entities.DoadorEntity;
import com.apajac.acolhimento.exceptions.NotFoundException;
import com.apajac.acolhimento.repositories.DoadorRepository;
import com.apajac.acolhimento.services.interfaces.RemoverDoadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RemoverDoadorServiceImpl implements RemoverDoadorService {

    private final DoadorRepository doadorRepository;


    @Override
    public void remover(Long id) {
        Optional<DoadorEntity> optionalDoador = doadorRepository.findById(id);
        if (optionalDoador.isEmpty())
        {
            throw new NotFoundException("Doador não encontrado.");
        }
        doadorRepository.delete(optionalDoador.get());
    }
}
