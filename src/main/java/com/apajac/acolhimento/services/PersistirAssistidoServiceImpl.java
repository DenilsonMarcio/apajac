package com.apajac.acolhimento.services;

import com.apajac.acolhimento.domain.dtos.*;
import com.apajac.acolhimento.domain.entities.*;
import com.apajac.acolhimento.domain.enums.TipoParentesco;
import com.apajac.acolhimento.exceptions.BusinessException;
import com.apajac.acolhimento.exceptions.NotFoundException;
import com.apajac.acolhimento.repositories.*;
import com.apajac.acolhimento.services.interfaces.PersistirAssistidoService;
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
public class PersistirAssistidoServiceImpl implements PersistirAssistidoService {

    private final AssistidoRepository assistidoRepository;
    private final FamiliarRepository familiarRepository;
    private final ComposicaoFamiliarRepository composicaoFamiliarRepository;
    private final ContatoRepository contatoRepository;
    private final ResponsavelRepository responsavelRepository;

    @Override
    public void persistirAssistido(AssistidoDTO assistidoDTO) {
        try {
            AssistidoEntity acolhido = inserirAcolhido(assistidoDTO);
            inserirFamiliares(assistidoDTO, acolhido);
            inserirComposicaoFamiliar(assistidoDTO, acolhido);
            cadastrarResponsavel(assistidoDTO.getResponsavel(), acolhido);
        } catch (Exception ex) {
            throw new BusinessException(format("Não foi possivel inserir o Acolhido: %s", ex.getMessage()));
        }
    }

    private AssistidoEntity inserirAcolhido(AssistidoDTO assistidoDTO) {
        if (isNull(assistidoDTO.getId())){
            return createAcolhido(assistidoDTO);
        }
        return updateAcolhido(assistidoDTO);
    }

    private void inserirFamiliares(AssistidoDTO assistidoDTO, AssistidoEntity acolhido) {
        if (isNull(assistidoDTO.getId())){
            createFamiliares(assistidoDTO.getFamiliares(), acolhido);
        } else {
            updateFamiliares(assistidoDTO.getFamiliares(), acolhido);
        }
    }

    private void inserirComposicaoFamiliar(AssistidoDTO assistidoDTO, AssistidoEntity acolhido) {
        if (isNull(assistidoDTO.getId())){
            createComposicaoFamiliar(assistidoDTO.getComposicaoFamiliar(), acolhido);
        } else {
            updateComposicaoFamiliar(assistidoDTO.getComposicaoFamiliar(), acolhido);
        }
    }

    private AssistidoEntity createAcolhido(AssistidoDTO assistidoDTO) {
        AssistidoEntity assistidoEntity = new AssistidoEntity();

        assistidoEntity.setNome(assistidoDTO.getNome());
        assistidoEntity.setDataNascimento(assistidoDTO.getDataNascimento());
        assistidoEntity.setEscolaridade(assistidoDTO.getEscolaridade());
        assistidoEntity.setEscola(assistidoDTO.getEscola());
        assistidoEntity.setTelEscola(assistidoDTO.getTelEscola());
        assistidoEntity.setCadastroInstituicao(assistidoDTO.isCadastroInstituicao());
        assistidoEntity.setInstituicao(assistidoDTO.getInstituicao());
        assistidoEntity.setEncaminhadoPara(assistidoDTO.getEncaminhadoPara());
        assistidoEntity.setQuemIndicouApajac(assistidoDTO.getQuemIndicouApajac());
        assistidoEntity.setInformacoesFornecidasPor(assistidoDTO.getInformacoesFornecidasPor());
        assistidoEntity.setEndereco(getEndereco(assistidoDTO.getEndereco()));
        assistidoEntity.setObservacoes(assistidoDTO.getObservacoes());

        return assistidoRepository.save(assistidoEntity);
    }
    private void createFamiliares(List<FamiliarDTO> familiares, AssistidoEntity assistido) {
        for (FamiliarDTO familiarDTO : familiares) {
            FamiliarEntity familiarEntity = new FamiliarEntity();
            familiarEntity.setNome(familiarDTO.getNome());
            familiarEntity.setOcupacao(familiarDTO.getOcupacao());
            familiarEntity.setLocalTrabalho(familiarDTO.getLocalTrabalho());
            familiarEntity.setSalario(familiarDTO.getSalario());
            familiarEntity.setVinculoEmpregaticio(familiarDTO.getVinculoEmpregaticio());
            familiarEntity.setTipoParentesco(TipoParentesco.valueOf(familiarDTO.getTipoParentesco()));
            familiarEntity.setAssistido(assistido);
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

    private void createComposicaoFamiliar(List<ComposicaoFamiliarDTO> composicaoFamiliarDTOS, AssistidoEntity assistido) {
        List<ComposicaoFamiliarEntity> composicaoFamiliar = new ArrayList<>();
        for (ComposicaoFamiliarDTO composicaoFamiliarDTO : composicaoFamiliarDTOS) {
            ComposicaoFamiliarEntity composicaoFamiliarEntity = new ComposicaoFamiliarEntity();
            composicaoFamiliarEntity.setNome(composicaoFamiliarDTO.getNome());
            composicaoFamiliarEntity.setAnoNascimento(composicaoFamiliarDTO.getAnoNascimento());
            composicaoFamiliarEntity.setParentesco(composicaoFamiliarDTO.getParentesco());
            composicaoFamiliarEntity.setOcupacao(composicaoFamiliarDTO.getOcupacao());
            composicaoFamiliarEntity.setAssistido(assistido);
            composicaoFamiliarEntity.setObservacoes(composicaoFamiliarDTO.getObservacoes());
            composicaoFamiliar.add(composicaoFamiliarEntity);
        }
        composicaoFamiliarRepository.saveAll(composicaoFamiliar);
    }

    private void cadastrarResponsavel(ResponsavelDTO responsavelDTO, AssistidoEntity assistido) {
        ResponsavelEntity entity = assistido.getResponsavel();
        if (isNull(entity)){
            ResponsavelEntity responsavelEntity = new ResponsavelEntity();
            responsavelEntity.setNome(responsavelDTO.getNome());
            responsavelEntity.setTipoParentesco(TipoParentesco.valueOf(responsavelDTO.getTipoParentesco()));
            responsavelEntity.setAssistido(assistido);
            ResponsavelEntity responsavel = responsavelRepository.save(responsavelEntity);
            persistirContatos(responsavelDTO, responsavel);
        } else {
            entity.setNome(responsavelDTO.getNome());
            entity.setTipoParentesco(TipoParentesco.valueOf(responsavelDTO.getTipoParentesco()));
            entity.setAssistido(assistido);
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

    private AssistidoEntity updateAcolhido(AssistidoDTO assistidoDTO) {
        Optional<AssistidoEntity> optionalAcolhido = assistidoRepository.findById(assistidoDTO.getId());
        if (optionalAcolhido.isEmpty()){
            throw new NotFoundException("Acolhido não encontrado.");
        }
        AssistidoEntity entity = optionalAcolhido.get();
        entity.setId(assistidoDTO.getId());
        entity.setNome(assistidoDTO.getNome());
        entity.setDataNascimento(assistidoDTO.getDataNascimento());
        entity.setEscolaridade(assistidoDTO.getEscolaridade());
        entity.setEscola(assistidoDTO.getEscola());
        entity.setTelEscola(assistidoDTO.getTelEscola());
        entity.setCadastroInstituicao(assistidoDTO.isCadastroInstituicao());
        entity.setInstituicao(assistidoDTO.getInstituicao());
        entity.setEncaminhadoPara(assistidoDTO.getEncaminhadoPara());
        entity.setQuemIndicouApajac(assistidoDTO.getQuemIndicouApajac());
        entity.setInformacoesFornecidasPor(assistidoDTO.getInformacoesFornecidasPor());
        entity.setEndereco(getEndereco(assistidoDTO.getEndereco()));
        entity.setObservacoes(assistidoDTO.getObservacoes());

        return assistidoRepository.save(entity);
    }

    private void updateFamiliares(List<FamiliarDTO> familiarDTOS, AssistidoEntity assistido) {
        List<FamiliarEntity> familiarEntities = familiarRepository.findByAssistido(assistido);
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
            familiarEntity.setAssistido(assistido);
            FamiliarEntity familiar = familiarRepository.save(familiarEntity);
            persistirContatos(familiarDTO, familiar);
        }
    }

    private void updateComposicaoFamiliar(List<ComposicaoFamiliarDTO> composicaoFamiliarDTOS, AssistidoEntity assistido) {
        List<ComposicaoFamiliarEntity> composicaoFamiliarEntities = composicaoFamiliarRepository.findByAssistido(assistido);
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
            composicaoFamiliarEntity.setAssistido(assistido);
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
