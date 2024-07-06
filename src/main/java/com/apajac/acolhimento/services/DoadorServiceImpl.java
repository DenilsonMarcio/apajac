package com.apajac.acolhimento.services;

import com.apajac.acolhimento.domain.entities.DoadorEntity;
import com.apajac.acolhimento.repositories.DoadorRepository;
import com.apajac.acolhimento.services.interfaces.DoadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DoadorServiceImpl implements DoadorService
{

    private final DoadorRepository doadorRepository;

    @Override
    public Page<DoadorEntity> listarDoadores(Pageable pageable)
    {
        return doadorRepository.findAll(pageable);
    }
}
