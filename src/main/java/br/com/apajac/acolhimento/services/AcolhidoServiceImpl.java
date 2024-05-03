package br.com.apajac.acolhimento.services;

import br.com.apajac.acolhimento.domain.dtos.ComposicaoFamiliarDTO;
import br.com.apajac.acolhimento.domain.dtos.ContratoAcolhidoDTO;
import br.com.apajac.acolhimento.domain.dtos.FamiliaresDTO;
import br.com.apajac.acolhimento.domain.entities.*;
import br.com.apajac.acolhimento.repositories.ComposicaoFamiliarRepository;
import br.com.apajac.acolhimento.repositories.FamiliaresRepository;
import br.com.apajac.acolhimento.repositories.ResponsavelRepository;
import br.com.apajac.acolhimento.services.interfaces.AcolhidoService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import br.com.apajac.acolhimento.repositories.AcolhidoRepository;

import java.util.Optional;

@Service
public class AcolhidoServiceImpl implements AcolhidoService {

    private final AcolhidoRepository aRepository;
    private final FamiliaresRepository fRepository;
    private final ResponsavelRepository rRepository;
    private final ComposicaoFamiliarRepository cfRepository;


    public AcolhidoServiceImpl(AcolhidoRepository repository, FamiliaresRepository fRepository, ResponsavelRepository rRepository, ComposicaoFamiliarRepository cfRepository) {

        this.aRepository = repository;
        this.fRepository = fRepository;
        this.rRepository = rRepository;
        this.cfRepository = cfRepository;
    }

    @Override
    public void cadastrarAcolhido(ContratoAcolhidoDTO contrato) {

        Optional<ResponsavelEntity> responsavelExistenteBusca = rRepository.findByNome(contrato.getResponsavel().getNome());

        AcolhidoEntity acolhidoEntity = new AcolhidoEntity();
        ResponsavelEntity responsavelEntity;

        //Cadastrar o responsavel
        if(responsavelExistenteBusca.isPresent()) {
            ResponsavelEntity responsavelExistente = responsavelExistenteBusca.get();
            acolhidoEntity.setResponsavel(responsavelExistente);

            //Cadastrar o acolhido
            BeanUtils.copyProperties(contrato.getAcolhido(), acolhidoEntity);
            if (contrato.getAcolhido().getEndereco() != null) {
                cadastrarAcolhidoFunc(contrato);
                acolhidoEntity.setResponsavel(responsavelExistente);
            }
            aRepository.save(acolhidoEntity);

        }
        else {
            responsavelEntity = new ResponsavelEntity();
            BeanUtils.copyProperties(contrato.getResponsavel(), responsavelEntity);
            rRepository.save(responsavelEntity);

            //Cadastrar o acolhido
            if (contrato.getAcolhido().getEndereco() != null) {
                cadastrarAcolhidoFunc(contrato);
            }
            aRepository.save(acolhidoEntity);

        }

        //Cadastrar os familiares
        for (FamiliaresDTO familiar : contrato.getFamiliares()) {
            FamiliaresEntity familiarEntity = new FamiliaresEntity();
            BeanUtils.copyProperties(familiar, familiarEntity);
            familiarEntity.setAcolhido(acolhidoEntity);
            fRepository.save(familiarEntity);
        }

        //Cadastrar Composição Familiar
        for(ComposicaoFamiliarDTO composicaoFamiliar : contrato.getComposicaoFamiliar())
        {
            ComposicaoFamiliarEntity composicaoFamiliarEntity = new ComposicaoFamiliarEntity();
            BeanUtils.copyProperties(composicaoFamiliar, composicaoFamiliarEntity);
            composicaoFamiliarEntity.setAcolhido(acolhidoEntity);
            cfRepository.save(composicaoFamiliarEntity);
        }
    }

    public void cadastrarAcolhidoFunc(ContratoAcolhidoDTO contrato)
    {
        AcolhidoEntity acolhidoEntity = new AcolhidoEntity();
        BeanUtils.copyProperties(contrato.getAcolhido(), acolhidoEntity);
        EnderecoEntity enderecoEntity = new EnderecoEntity();
        BeanUtils.copyProperties(contrato.getAcolhido().getEndereco(), enderecoEntity);
        acolhidoEntity.setEndereco(enderecoEntity);
    }

}