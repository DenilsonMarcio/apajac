package com.apajac.acolhimento.services;

import com.apajac.acolhimento.domain.entities.DoadorEntity;
import com.apajac.acolhimento.repositories.DoadorRepository;
import com.apajac.acolhimento.services.interfaces.ListarDoadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListarDoadorServiceImpl implements ListarDoadorService
{

    private final DoadorRepository doadorRepository;

    @Override
    public Page<DoadorEntity> listarDoadores(Pageable pageable)
    {
        return doadorRepository.findAll(pageable);
    }
}
