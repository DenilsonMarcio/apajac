package com.apajac.acolhimento.services;

import com.apajac.acolhimento.domain.dtos.DoadorDTO;
import com.apajac.acolhimento.domain.entities.DoadorEntity;
import com.apajac.acolhimento.repositories.DoadorRepository;
import com.apajac.acolhimento.services.interfaces.DoadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DoadorServiceImpl implements DoadorService
{

    private final DoadorRepository doadorRepository;

@Override
    public void createDoador(DoadorDTO doadorDTO)
    {
        DoadorEntity doadorEntity = new DoadorEntity();
        doadorEntity.setNome(doadorDTO.getNome());
        doadorEntity.setDocumento(doadorDTO.getDocumento());
        doadorEntity.setValor(doadorDTO.getValor());
        doadorEntity.setTipo_doador(doadorDTO.getTipo_doador());
        doadorEntity.setComo_conheceu(doadorDTO.getComo_conheceu());
        doadorRepository.save(doadorEntity);
    }
}