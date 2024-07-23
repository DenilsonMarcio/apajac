package com.apajac.acolhimento.services;

import com.apajac.acolhimento.domain.entities.DoadorEntity;
import com.apajac.acolhimento.exceptions.NotFoundException;
import com.apajac.acolhimento.repositories.DoadorRepository;
import com.apajac.acolhimento.services.interfaces.RemoverDoadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class RemoverDoadorServiceImpl implements RemoverDoadorService
{

    private final DoadorRepository doadorRepository;

    @Override
    public void removerDoador(Long id)
    {
        Optional<DoadorEntity> doador = doadorRepository.findById(id);
        if (doador.isEmpty())
        {
            throw new NotFoundException("Doador não encontrado.");
        }
        doadorRepository.delete(doador.get());
    }
}
