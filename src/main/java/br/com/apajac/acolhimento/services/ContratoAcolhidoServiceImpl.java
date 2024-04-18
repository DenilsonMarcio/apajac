package br.com.apajac.acolhimento.services;

import br.com.apajac.acolhimento.domain.dtos.ComposicaoFamiliarDTO;
import br.com.apajac.acolhimento.domain.dtos.ContratoAcolhidoDTO;
import br.com.apajac.acolhimento.domain.dtos.FamiliaresDTO;
import br.com.apajac.acolhimento.domain.entities.*;
import br.com.apajac.acolhimento.repositories.*;
import br.com.apajac.acolhimento.services.interfaces.ContratoAcolhidoService;
import jakarta.transaction.Transactional;


import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ContratoAcolhidoServiceImpl implements ContratoAcolhidoService {

    private final ContratoAcolhidoRepository repository;
    private final AcolhidoServiceImpl aervice;
    private final FamiliaresServiceImpl fService;
    private final ResponsavelServiceImpl respService;
    private final ComposicaoFamiliarServiceImpl cfService;


    public ContratoAcolhidoServiceImpl(ContratoAcolhidoRepository repository, AcolhidoServiceImpl aervice, FamiliaresServiceImpl fService, ResponsavelServiceImpl respService, ComposicaoFamiliarServiceImpl cfService) {

        this.repository = repository;
        this.aervice = aervice;
        this.fService = fService;
        this.respService = respService;
        this.cfService = cfService;
    }

    @Override
    public ContratoAcolhidoEntity cadastrarContrato(ContratoAcolhidoDTO contratoAcolhido) {

        ContratoAcolhidoEntity contratoAcolhidoEntity = new ContratoAcolhidoEntity();
        BeanUtils.copyProperties(contratoAcolhido, contratoAcolhidoEntity);

        aervice.cadastrarAcolhido(contratoAcolhido.getAcolhido());

        for (FamiliaresDTO familiar : contratoAcolhido.getFamiliares()) {
            fService.cadastrarFamiliar(familiar);
        }

        respService.cadastrarResponsavel(contratoAcolhido.getResponsavel());

        for (ComposicaoFamiliarDTO composicaoFamilar : contratoAcolhido.getComposicaoFamiliar())
        {
            cfService.cadastrarComposicaoFamiliar(composicaoFamilar);
        }

        return repository.save(contratoAcolhidoEntity);
    }
}
