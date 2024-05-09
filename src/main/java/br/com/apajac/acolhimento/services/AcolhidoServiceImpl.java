package br.com.apajac.acolhimento.services;

import br.com.apajac.acolhimento.domain.dtos.ComposicaoFamiliarDTO;
import br.com.apajac.acolhimento.domain.dtos.ContratoAcolhidoDTO;
import br.com.apajac.acolhimento.domain.dtos.FamiliaresDTO;
import br.com.apajac.acolhimento.domain.entities.*;
import br.com.apajac.acolhimento.repositories.*;
import br.com.apajac.acolhimento.services.interfaces.AcolhidoService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AcolhidoServiceImpl implements AcolhidoService {

    private final AcolhidoRepository aRepository;
    private final EnderecoRepository endRepository;
    private final FamiliaresRepository fRepository;
    private final ResponsavelRepository rRepository;
    private final ComposicaoFamiliarRepository cfRepository;


    public AcolhidoServiceImpl(AcolhidoRepository aRepository, EnderecoRepository endRepository, FamiliaresRepository fRepository, ResponsavelRepository rRepository, ComposicaoFamiliarRepository cfRepository) {

        this.aRepository = aRepository;
        this.endRepository = endRepository;
        this.fRepository = fRepository;
        this.rRepository = rRepository;
        this.cfRepository = cfRepository;
    }

    @Override
    public void cadastrarAcolhido(ContratoAcolhidoDTO contrato) {

        Optional<ResponsavelEntity> responsavelExistenteBusca = rRepository.findByNome(contrato.getResponsavel().getNome());

        AcolhidoEntity acolhidoEntity = new AcolhidoEntity();
        ResponsavelEntity responsavelEntity;

        //Já existe responsavel
        if(responsavelExistenteBusca.isPresent()) {
            ResponsavelEntity responsavelExistente = responsavelExistenteBusca.get();

            //Cadastrar o acolhido e
            BeanUtils.copyProperties(contrato.getAcolhido(), acolhidoEntity);
            if (contrato.getAcolhido().getEndereco() != null) {
                acolhidoEntity = funcaoCadastrarAcolhido(contrato);
                aRepository.save(acolhidoEntity);
                endRepository.save(acolhidoEntity.getEndereco());

                //Cadastrar os familiares
                for (FamiliaresDTO familiar : contrato.getFamiliares()) {
                    FamiliaresEntity familiarEntity = new FamiliaresEntity();
                    BeanUtils.copyProperties(familiar, familiarEntity);
                    familiarEntity.setAcolhido(acolhidoEntity);
                    fRepository.save(familiarEntity);
                }

                //setar o responsavel
                acolhidoEntity.setResponsavel(responsavelExistente);


                //Cadastrar Composição Familiar
                for(ComposicaoFamiliarDTO composicaoFamiliar : contrato.getComposicaoFamiliar())
                {
                    ComposicaoFamiliarEntity composicaoFamiliarEntity = new ComposicaoFamiliarEntity();
                    BeanUtils.copyProperties(composicaoFamiliar, composicaoFamiliarEntity);
                    composicaoFamiliarEntity.setAcolhido(acolhidoEntity);
                    cfRepository.save(composicaoFamiliarEntity);
                }

            }
        }

        else {
            BeanUtils.copyProperties(contrato.getAcolhido(), acolhidoEntity);
            if (contrato.getAcolhido().getEndereco() != null) {
                //Cadastrar o acolhido
                acolhidoEntity = funcaoCadastrarAcolhido(contrato);
                aRepository.save(acolhidoEntity);
                endRepository.save(acolhidoEntity.getEndereco());

                //Cadastrar os familiares
                for (FamiliaresDTO familiar : contrato.getFamiliares()) {
                    FamiliaresEntity familiarEntity = new FamiliaresEntity();
                    BeanUtils.copyProperties(familiar, familiarEntity);
                    familiarEntity.setAcolhido(acolhidoEntity);
                    fRepository.save(familiarEntity);
                }

                //Cadastrar o responsavel
                    responsavelEntity = new ResponsavelEntity();
                    BeanUtils.copyProperties(contrato.getResponsavel(), responsavelEntity);
                    rRepository.save(responsavelEntity);
                    acolhidoEntity.setResponsavel(responsavelEntity);

                //Cadastrar Composição Familiar
                for(ComposicaoFamiliarDTO composicaoFamiliar : contrato.getComposicaoFamiliar())
                {
                    ComposicaoFamiliarEntity composicaoFamiliarEntity = new ComposicaoFamiliarEntity();
                    BeanUtils.copyProperties(composicaoFamiliar, composicaoFamiliarEntity);
                    composicaoFamiliarEntity.setAcolhido(acolhidoEntity);
                    cfRepository.save(composicaoFamiliarEntity);
                }

            }
        }


    }

    public AcolhidoEntity funcaoCadastrarAcolhido(ContratoAcolhidoDTO contrato) {
        AcolhidoEntity acolhidoEntity = new AcolhidoEntity();
        BeanUtils.copyProperties(contrato.getAcolhido(), acolhidoEntity);
        EnderecoEntity enderecoEntity = new EnderecoEntity();
        BeanUtils.copyProperties(contrato.getAcolhido().getEndereco(), enderecoEntity);
        acolhidoEntity.setEndereco(enderecoEntity);
        return acolhidoEntity;
    }


}