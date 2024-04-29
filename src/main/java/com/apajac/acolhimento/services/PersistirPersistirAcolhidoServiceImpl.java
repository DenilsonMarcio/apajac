package com.apajac.acolhimento.services;

import com.apajac.acolhimento.domain.dtos.*;
import com.apajac.acolhimento.domain.entities.*;
import com.apajac.acolhimento.domain.enums.TipoParentesco;
import com.apajac.acolhimento.exceptions.BusinessException;
import com.apajac.acolhimento.exceptions.NotFoundException;
import com.apajac.acolhimento.repositories.*;
import com.apajac.acolhimento.services.interfaces.PersistirAcolhidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;
import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
@Transactional
public class PersistirPersistirAcolhidoServiceImpl implements PersistirAcolhidoService {

    private final AcolhidoRepository acolhidoRepository;
    private final FamiliarRepository familiarRepository;
    private final ComposicaoFamiliarRepository composicaoFamiliarRepository;
    private final ContatoRepository contatoRepository;
    private final ResponsavelRepository responsavelRepository;

    @Override
    public void persistirAcolhido(AcolhidoDTO acolhidoDTO) {
        try {
            AcolhidoEntity acolhido = inserirAcolhido(acolhidoDTO);
            inserirFamiliares(acolhidoDTO, acolhido);
            inserirComposicaoFamiliar(acolhidoDTO, acolhido);
            cadastrarResponsavel(acolhidoDTO.getResponsavel(), acolhido);
        } catch (Exception ex) {
            throw new BusinessException(format("Não foi possivel inserir o Acolhido: %s", ex.getMessage()));
        }
    }

    private AcolhidoEntity inserirAcolhido(AcolhidoDTO acolhidoDTO) {
        if (isNull(acolhidoDTO.getId())){
            return createAcolhido(acolhidoDTO);
        }
        return updateAcolhido(acolhidoDTO);
    }

    private void inserirFamiliares(AcolhidoDTO acolhidoDTO, AcolhidoEntity acolhido) {
        if (isNull(acolhidoDTO.getId())){
            createFamiliares(acolhidoDTO.getFamiliares(), acolhido);
        } else {
            updateFamiliares(acolhidoDTO.getFamiliares(), acolhido);
        }
    }

    private void inserirComposicaoFamiliar(AcolhidoDTO acolhidoDTO, AcolhidoEntity acolhido) {
        if (isNull(acolhidoDTO.getId())){
            createComposicaoFamiliar(acolhidoDTO.getComposicaoFamiliar(), acolhido);
        } else {
            updateComposicaoFamiliar(acolhidoDTO.getComposicaoFamiliar(), acolhido);
        }
    }

    private AcolhidoEntity createAcolhido(AcolhidoDTO acolhidoDTO) {
        AcolhidoEntity acolhidoEntity = new AcolhidoEntity();

        acolhidoEntity.setNome(acolhidoDTO.getNome());
        acolhidoEntity.setDataNascimento(acolhidoDTO.getDataNascimento());
        acolhidoEntity.setEscolaridade(acolhidoDTO.getEscolaridade());
        acolhidoEntity.setEscola(acolhidoDTO.getEscola());
        acolhidoEntity.setTelEscola(acolhidoDTO.getTelEscola());
        acolhidoEntity.setCadastroInstituicao(acolhidoDTO.isCadastroInstituicao());
        acolhidoEntity.setInstituicao(acolhidoDTO.getInstituicao());
        acolhidoEntity.setEncaminhadoPara(acolhidoDTO.getEncaminhadoPara());
        acolhidoEntity.setQuemIndicouApajac(acolhidoDTO.getQuemIndicouApajac());
        acolhidoEntity.setInformacoesFornecidasPor(acolhidoDTO.getInformacoesFornecidasPor());
        acolhidoEntity.setEndereco(getEndereco(acolhidoDTO.getEndereco()));
        acolhidoEntity.setObservacoes(acolhidoDTO.getObservacoes());

        return acolhidoRepository.save(acolhidoEntity);
    }
    private void createFamiliares(List<FamiliarDTO> familiares, AcolhidoEntity acolhido) {
        for (FamiliarDTO familiarDTO : familiares) {
            FamiliarEntity familiarEntity = new FamiliarEntity();
            familiarEntity.setNome(familiarDTO.getNome());
            familiarEntity.setOcupacao(familiarDTO.getOcupacao());
            familiarEntity.setLocalTrabalho(familiarDTO.getLocalTrabalho());
            familiarEntity.setSalario(familiarDTO.getSalario());
            familiarEntity.setVinculoEmpregaticio(familiarDTO.getVinculoEmpregaticio());
            familiarEntity.setTipoParentesco(TipoParentesco.valueOf(familiarDTO.getTipoParentesco()));
            familiarEntity.setAcolhido(acolhido);
            FamiliarEntity familiar = familiarRepository.save(familiarEntity);
            persistirContatos(familiarDTO, familiar);
        }
    }

    private void persistirContatos(FamiliarDTO familiarDTO, FamiliarEntity familiar) {
        List<ContatoEntity> contatosList = contatoRepository.findByFamiliar(familiar);
        if (!isNull(contatosList)){
            contatoRepository.deleteAll(contatosList);
        }
        List<ContatoDTO> contatos = familiarDTO.getContatos();
        List<ContatoEntity> contatoEntities = new ArrayList<>();
        for (ContatoDTO dto : contatos) {
            ContatoEntity entity = new ContatoEntity();
            entity.setContato(dto.getContato());
            entity.setFamiliar(familiar);
            contatoEntities.add(entity);
        }
        contatoRepository.saveAll(contatoEntities);
    }

    private void createComposicaoFamiliar(List<ComposicaoFamiliarDTO> composicaoFamiliarDTOS, AcolhidoEntity acolhido) {
        List<ComposicaoFamiliarEntity> composicaoFamiliar = new ArrayList<>();
        for (ComposicaoFamiliarDTO composicaoFamiliarDTO : composicaoFamiliarDTOS) {
            ComposicaoFamiliarEntity composicaoFamiliarEntity = new ComposicaoFamiliarEntity();
            composicaoFamiliarEntity.setNome(composicaoFamiliarDTO.getNome());
            composicaoFamiliarEntity.setAnoNascimento(composicaoFamiliarDTO.getAnoNascimento());
            composicaoFamiliarEntity.setParentesco(composicaoFamiliarDTO.getParentesco());
            composicaoFamiliarEntity.setOcupacao(composicaoFamiliarDTO.getOcupacao());
            composicaoFamiliarEntity.setAcolhido(acolhido);
            composicaoFamiliarEntity.setObservacoes(composicaoFamiliarDTO.getObservacoes());
            composicaoFamiliar.add(composicaoFamiliarEntity);
        }
        composicaoFamiliarRepository.saveAll(composicaoFamiliar);
    }

    private void cadastrarResponsavel(ResponsavelDTO responsavelDTO, AcolhidoEntity acolhido) {
        ResponsavelEntity entity = acolhido.getResponsavel();
        if (isNull(entity)){
            ResponsavelEntity responsavelEntity = new ResponsavelEntity();
            responsavelEntity.setNome(responsavelDTO.getNome());
            responsavelEntity.setTipoParentesco(TipoParentesco.valueOf(responsavelDTO.getTipoParentesco()));
            responsavelEntity.setAcolhido(acolhido);
            ResponsavelEntity responsavel = responsavelRepository.save(responsavelEntity);
            persistirContatos(responsavelDTO, responsavel);
        } else {
            entity.setNome(responsavelDTO.getNome());
            entity.setTipoParentesco(TipoParentesco.valueOf(responsavelDTO.getTipoParentesco()));
            entity.setAcolhido(acolhido);
            ResponsavelEntity responsavel = responsavelRepository.save(entity);
            persistirContatos(responsavelDTO, responsavel);
        }
    }

    private void persistirContatos(ResponsavelDTO responsavelDTO, ResponsavelEntity responsavel) {
        List<ContatoEntity> contatosList = contatoRepository.findByResponsavel(responsavel);
        if (!isNull(contatosList)){
            contatoRepository.deleteAll(contatosList);
        }
        List<ContatoDTO> contatos = responsavelDTO.getContatos();
        List<ContatoEntity> contatoEntities = new ArrayList<>();
        for (ContatoDTO dto : contatos) {
            ContatoEntity entity = new ContatoEntity();
            entity.setContato(dto.getContato());
            entity.setResponsavel(responsavel);
            contatoEntities.add(entity);
        }
        contatoRepository.saveAll(contatoEntities);
    }

    private AcolhidoEntity updateAcolhido(AcolhidoDTO acolhidoDTO) {
        Optional<AcolhidoEntity> optionalAcolhido = acolhidoRepository.findById(acolhidoDTO.getId());
        if (optionalAcolhido.isEmpty()){
            throw new NotFoundException("Acolhido não encontrado.");
        }
        AcolhidoEntity entity = optionalAcolhido.get();
        entity.setId(acolhidoDTO.getId());
        entity.setNome(acolhidoDTO.getNome());
        entity.setDataNascimento(acolhidoDTO.getDataNascimento());
        entity.setEscolaridade(acolhidoDTO.getEscolaridade());
        entity.setEscola(acolhidoDTO.getEscola());
        entity.setTelEscola(acolhidoDTO.getTelEscola());
        entity.setCadastroInstituicao(acolhidoDTO.isCadastroInstituicao());
        entity.setInstituicao(acolhidoDTO.getInstituicao());
        entity.setEncaminhadoPara(acolhidoDTO.getEncaminhadoPara());
        entity.setQuemIndicouApajac(acolhidoDTO.getQuemIndicouApajac());
        entity.setInformacoesFornecidasPor(acolhidoDTO.getInformacoesFornecidasPor());
        entity.setEndereco(getEndereco(acolhidoDTO.getEndereco()));
        entity.setObservacoes(acolhidoDTO.getObservacoes());

        return acolhidoRepository.save(entity);
    }

    private void updateFamiliares(List<FamiliarDTO> familiarDTOS, AcolhidoEntity acolhido) {
        List<FamiliarEntity> familiarEntities = familiarRepository.findByAcolhido(acolhido);
        if(!isNull(familiarEntities)){
            familiarRepository.deleteAll(familiarEntities);
        }
        for (FamiliarDTO familiarDTO : familiarDTOS) {
            FamiliarEntity familiarEntity = new FamiliarEntity();
            familiarEntity.setNome(familiarDTO.getNome());
            familiarEntity.setOcupacao(familiarDTO.getOcupacao());
            familiarEntity.setLocalTrabalho(familiarDTO.getLocalTrabalho());
            familiarEntity.setSalario(familiarDTO.getSalario());
            familiarEntity.setVinculoEmpregaticio(familiarDTO.getVinculoEmpregaticio());
            familiarEntity.setTipoParentesco(TipoParentesco.valueOf(familiarDTO.getTipoParentesco()));
            familiarEntity.setAcolhido(acolhido);
            FamiliarEntity familiar = familiarRepository.save(familiarEntity);
            persistirContatos(familiarDTO, familiar);
        }
    }

    private void updateComposicaoFamiliar(List<ComposicaoFamiliarDTO> composicaoFamiliarDTOS, AcolhidoEntity acolhido) {
        List<ComposicaoFamiliarEntity> composicaoFamiliarEntities = composicaoFamiliarRepository.findByAcolhido(acolhido);
        if(!isNull(composicaoFamiliarEntities)){
            composicaoFamiliarRepository.deleteAll(composicaoFamiliarEntities);
        }
        composicaoFamiliarRepository.deleteAll(composicaoFamiliarEntities);
        for (ComposicaoFamiliarDTO composicaoFamiliarDTO : composicaoFamiliarDTOS) {
            ComposicaoFamiliarEntity composicaoFamiliarEntity = new ComposicaoFamiliarEntity();
            composicaoFamiliarEntity.setNome(composicaoFamiliarDTO.getNome());
            composicaoFamiliarEntity.setAnoNascimento(composicaoFamiliarDTO.getAnoNascimento());
            composicaoFamiliarEntity.setParentesco(composicaoFamiliarDTO.getParentesco());
            composicaoFamiliarEntity.setOcupacao(composicaoFamiliarDTO.getOcupacao());
            composicaoFamiliarEntity.setObservacoes(composicaoFamiliarDTO.getObservacoes());
            composicaoFamiliarEntity.setAcolhido(acolhido);
            composicaoFamiliarRepository.save(composicaoFamiliarEntity);
        }
    }

    private Endereco getEndereco(EnderecoDTO enderecoDTO) {
        Endereco endereco = new Endereco();
        endereco.setCep(enderecoDTO.getCep());
        endereco.setEndereco(enderecoDTO.getEndereco());
        endereco.setNumero(enderecoDTO.getNumero());
        endereco.setBairro(enderecoDTO.getBairro());
        endereco.setCidade(enderecoDTO.getCidade());
        endereco.setUF(enderecoDTO.getUF());
        endereco.setComplemento(enderecoDTO.getComplemento());
        return endereco;
    }
}
