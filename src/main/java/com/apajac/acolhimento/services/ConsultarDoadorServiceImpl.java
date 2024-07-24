package com.apajac.acolhimento.services;

import com.apajac.acolhimento.domain.entities.DoadorEntity;
import com.apajac.acolhimento.exceptions.NotFoundException;
import com.apajac.acolhimento.repositories.DoadorRepository;
import com.apajac.acolhimento.services.interfaces.ConsultarDoadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ConsultarDoadorServiceImpl implements ConsultarDoadorService {

    private final DoadorRepository doadorRepository;

    @Override
    public Page<DoadorEntity> listarDoadoresPage(Pageable pageable) {
        return doadorRepository.findAll(pageable);
    }

    @Override
    public DoadorEntity buscarDoadorPorId(Long id) {
        return doadorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Doador não encontrado."));
    }

}