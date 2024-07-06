package com.apajac.acolhimento.services;

import com.apajac.acolhimento.domain.entities.DoadorEntity;
import com.apajac.acolhimento.exceptions.NotFoundException;
import com.apajac.acolhimento.repositories.DoadorRepository;
import com.apajac.acolhimento.services.interfaces.DoadorService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class DoadorServiceImpl implements DoadorService
{

    private final DoadorRepository doadorRepository;

    @Override
    public DoadorEntity buscarDoadorPorId(Long id)
    {
        Optional<DoadorEntity> doadorOpt = doadorRepository.findById(id);
        if (doadorOpt.isEmpty())
        {
            throw new NotFoundException("Doador não encontrado.");
        }
        return doadorOpt.get();
    }
}
