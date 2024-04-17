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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class PersistirPersistirAcolhidoServiceImpl implements PersistirAcolhidoService {

    private final AcolhidoRepository acolhidoRepository;
    private final FamiliarRepository familiarRepository;
    private final ComposicaoFamiliarRepository composicaoFamiliarRepository;
    private final ContatoRepository contatoRepository;
    private final ResponsavelRepository responsavelRepository;

    @Override
    public void persistirAcolhido(AcolhidoDTO acolhidoDTO) {
        try {
            List<FamiliarDTO> familiares = acolhidoDTO.getFamiliares();
            List<ComposicaoFamiliarDTO> composicaoFamiliar = acolhidoDTO.getComposicaoFamiliar();
            ResponsavelDTO responsavel = acolhidoDTO.getResponsavel();

            AcolhidoEntity acolhido = inserirAcolhido(acolhidoDTO);
            cadastrarFamiliares(familiares, acolhido);
            cadastrarResponsavel(responsavel, acolhido);
            cadastrarComposicaoFamiliar(composicaoFamiliar, acolhido);
        } catch (Exception ex) {
            throw new BusinessException(format("Não foi possivel inserir o Acolhido: %s", ex.getMessage()));
        }
    }

    private AcolhidoEntity inserirAcolhido(AcolhidoDTO acolhidoDTO) {

        if (Objects.isNull(acolhidoDTO.getId())){
            return createAcolhido(acolhidoDTO);
        }
        return updateAcolhido(acolhidoDTO);
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

    private void cadastrarFamiliares(List<FamiliarDTO> familiarDTOs, AcolhidoEntity acolhido) {
        for (FamiliarDTO familiarDTO : familiarDTOs) {
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

    private List<ContatoEntity> persistirContatos(FamiliarDTO familiarDTO, FamiliarEntity familiar) {
        List<ContatoDTO> contatos = familiarDTO.getContatos();
        List<ContatoEntity> contatoEntities = new ArrayList<>();
        for (ContatoDTO dto : contatos) {
            ContatoEntity entity = new ContatoEntity();
            entity.setContato(dto.getContato());
            entity.setFamiliar(familiar);
            contatoEntities.add(entity);
        }
        return contatoRepository.saveAll(contatoEntities);
    }

    private List<ContatoEntity> persistirContatos(ResponsavelDTO responsavelDTO, ResponsavelEntity responsavel) {
        List<ContatoDTO> contatos = responsavelDTO.getContatos();
        List<ContatoEntity> contatoEntities = new ArrayList<>();
        for (ContatoDTO dto : contatos) {
            ContatoEntity entity = new ContatoEntity();
            entity.setContato(dto.getContato());
            entity.setResponsavel(responsavel);
            contatoEntities.add(entity);
        }
        return contatoRepository.saveAll(contatoEntities);
    }

    private void cadastrarComposicaoFamiliar(List<ComposicaoFamiliarDTO> composicaoFamiliarDTOs, AcolhidoEntity acolhido) {
        List<ComposicaoFamiliarEntity> composicaoFamiliar = new ArrayList<>();
        for (ComposicaoFamiliarDTO composicaoFamiliarDTO : composicaoFamiliarDTOs) {
            ComposicaoFamiliarEntity composicaoFamiliarEntity = new ComposicaoFamiliarEntity();
            composicaoFamiliarEntity.setNome(composicaoFamiliarDTO.getNome());
            composicaoFamiliarEntity.setAnoNascimento(composicaoFamiliarDTO.getAnoNascimento());
            composicaoFamiliarEntity.setParentesco(composicaoFamiliarDTO.getParentesco());
            composicaoFamiliarEntity.setOcupacao(composicaoFamiliarDTO.getOcupacao());
            composicaoFamiliarEntity.setAcolhido(acolhido);
            composicaoFamiliar.add(composicaoFamiliarEntity);
        }
        composicaoFamiliarRepository.saveAll(composicaoFamiliar);
    }

    private void cadastrarResponsavel(ResponsavelDTO responsavelDTO, AcolhidoEntity acolhido) {
        ResponsavelEntity entity = acolhido.getResponsavel();
        if (Objects.isNull(entity)){
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
}
