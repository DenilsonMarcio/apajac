package br.com.apajac.acolhimento.services;

import br.com.apajac.acolhimento.domain.dtos.ComposicaoFamiliarDTO;
import br.com.apajac.acolhimento.domain.dtos.ContratoAcolhidoDTO;
import br.com.apajac.acolhimento.domain.entities.*;
import br.com.apajac.acolhimento.repositories.*;
import br.com.apajac.acolhimento.services.interfaces.ContratoAcolhidoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class ContratoAcolhidoServiceImpl implements ContratoAcolhidoService {

    private final AcolhidoRepository aRepository;
    private final MaeRepository mRepository;
    private final PaiRepository pRepository;
    private final ResponsavelRepository respRepository;
    private final ComposicaoFamiliarRepository cfRepository;

    public ContratoAcolhidoServiceImpl(AcolhidoRepository aRepository, MaeRepository mRepository, PaiRepository pRepository, ResponsavelRepository respRepository, ComposicaoFamiliarRepository cfRepository) {
        this.aRepository = aRepository;
        this.mRepository = mRepository;
        this.pRepository = pRepository;
        this.respRepository = respRepository;
        this.cfRepository = cfRepository;
    }

    @Override
    @Transactional
    public ContratoAcolhidoEntity cadastrarContrato(ContratoAcolhidoDTO contratoAcolhido)
    {
        ContratoAcolhidoEntity contratoAcolhidoEntity = new ContratoAcolhidoEntity();

        AcolhidoEntity aEntity = new AcolhidoEntity();
        BeanUtils.copyProperties(contratoAcolhido.getAcolhido(), aEntity);
        aRepository.save(aEntity);

        MaeEntity mEntity = new MaeEntity();
        BeanUtils.copyProperties(contratoAcolhido.getMae(), mEntity);
        mRepository.save(mEntity);

        PaiEntity pEntity = new PaiEntity();
        BeanUtils.copyProperties(contratoAcolhido.getPai(), pEntity);
        pRepository.save(pEntity);

        ResponsavelEntity respEntity = new ResponsavelEntity();
        BeanUtils.copyProperties(contratoAcolhido.getResponsavel(), respEntity);
        respRepository.save(respEntity);

        return contratoAcolhidoEntity;

    }
}
