package com.apajac.acolhimento.services;

import com.apajac.acolhimento.domain.dtos.*;
import com.apajac.acolhimento.domain.entities.*;
import com.apajac.acolhimento.domain.enums.AuditoriaEnum;
import com.apajac.acolhimento.domain.enums.TipoParentesco;
import com.apajac.acolhimento.exceptions.BusinessException;
import com.apajac.acolhimento.exceptions.NotFoundException;
import com.apajac.acolhimento.repositories.*;
import com.apajac.acolhimento.services.interfaces.AuditoriaService;
import com.apajac.acolhimento.services.interfaces.PersistirAssistidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

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

    private final AuditoriaService auditoria;
    @Override
    public void persistirAssistido(AssistidoDTO assistidoDTO) {
        try {
            AssistidoEntity assistido = inserirAssistido(assistidoDTO);
            inserirFamiliares(assistidoDTO, assistido);
            inserirComposicaoFamiliar(assistidoDTO, assistido);
            cadastrarResponsavel(assistidoDTO.getResponsavel(), assistido);
        } catch (Exception ex) {
            throw new BusinessException(format("Não foi possivel inserir o Assistido: %s", ex.getMessage()));
        }
    }

    private AssistidoEntity inserirAssistido(AssistidoDTO assistidoDTO) {
        if (isNull(assistidoDTO.getId())){
            return createAssistido(assistidoDTO);
        }
        return updateAssistido(assistidoDTO);
    }

    private void inserirFamiliares(AssistidoDTO assistidoDTO, AssistidoEntity assistido) {
        if (isNull(assistidoDTO.getId())){
            createFamiliares(assistidoDTO.getFamiliares(), assistido);
        } else {
            updateFamiliares(assistidoDTO.getFamiliares(), assistido);
        }
    }

    private void inserirComposicaoFamiliar(AssistidoDTO assistidoDTO, AssistidoEntity assistido) {
        if (isNull(assistidoDTO.getId())){
            createComposicaoFamiliar(assistidoDTO.getComposicaoFamiliar(), assistido);
        } else {
            updateComposicaoFamiliar(assistidoDTO.getComposicaoFamiliar(), assistido);
        }
    }

    private AssistidoEntity createAssistido(AssistidoDTO assistidoDTO) {
        AssistidoEntity assistidoEntity = new AssistidoEntity();

        assistidoEntity.setNome(assistidoDTO.getNome());
        assistidoEntity.setDataNascimento(assistidoDTO.getDataNascimento());
        assistidoEntity.setEscolaridade(assistidoDTO.getEscolaridade());
        assistidoEntity.setEscola(assistidoDTO.getEscola());
        assistidoEntity.setTelEscola(assistidoDTO.getTelEscola());
        assistidoEntity.setCadastroInstituicao(assistidoDTO.isCadastroInstituicao());
        if(assistidoEntity.isCadastroInstituicao() == true) {
            if(assistidoDTO.getInstituicao() == null || assistidoDTO.getInstituicao().isEmpty()){
                throw new BusinessException("O campo 'instituicao' em Assistido, é obrigatorio");
            }
        }
        assistidoEntity.setInstituicao(assistidoDTO.getInstituicao());
        assistidoEntity.setEncaminhadoPara(assistidoDTO.getEncaminhadoPara());
        assistidoEntity.setQuemIndicouApajac(assistidoDTO.getQuemIndicouApajac());
        assistidoEntity.setInformacoesFornecidasPor(assistidoDTO.getInformacoesFornecidasPor());
        assistidoEntity.setEndereco(getEndereco(assistidoDTO.getEndereco()));
        assistidoEntity.setObservacoes(assistidoDTO.getObservacoes());
        assistidoEntity.setIdResponsavelPeloCadastro(assistidoDTO.getIdResponsavelPeloCadastro());
        assistidoEntity.setCadastradoEm(assistidoDTO.getCadastradoEm());

        auditar(assistidoEntity.toString(),
                assistidoDTO.getIdResponsavelPeloCadastro(),
                AuditoriaEnum.CREATED.getValues());

        return assistidoRepository.save(assistidoEntity);

    }


    private void createFamiliares(List<FamiliarDTO> familiares, AssistidoEntity assistido) {

        for (FamiliarDTO familiarDTO : familiares) {

            // Verifica o tipo de parentesco
            if (!"PAI".equals(familiarDTO.getTipoParentesco())) {
                // Valida o nome
                if (familiarDTO.getNome() == null || familiarDTO.getNome().isEmpty()) {
                    throw new BusinessException(
                            String.format("O campo 'nome' em Familiar (%s), é obrigatório.", familiarDTO.getTipoParentesco())
                    );                }
                // Valida a lista de contatos
                if (familiarDTO.getContatos() == null || familiarDTO.getContatos().isEmpty()) {
                    throw new BusinessException(
                            String.format("A 'Lista de contatos' em Familiar (%s), é obrigatorio.", familiarDTO.getTipoParentesco())
                    );                 }
                // Valida os campos dentro de cada ContatoDTO
                for (ContatoDTO contato : familiarDTO.getContatos()) {
                    if (contato.getContato() == null || contato.getContato().isEmpty()) {
                        throw new BusinessException(
                                String.format("O campo 'contato' dentro da lista de contatos em Familiar (%s), é obrigatorio.", familiarDTO.getTipoParentesco())
                        );
                    }
                }
            }

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

        auditar(familiares.toString(),
                assistido.getIdResponsavelPeloCadastro(),
                AuditoriaEnum.CREATED.getValues());
    }

    private void validateAndPersist() {

    }

    private void persistirContatos(FamiliarDTO familiarDTO, FamiliarEntity familiar) {
        List<ContatoEntity> contatosList = contatoRepository.findByFamiliar(familiar);
        if (!isNull(contatosList)){
            contatoRepository.deleteAll(contatosList);
        }
        List<ContatoDTO> contatos = familiarDTO.getContatos();
        if (contatos != null) {
            List<ContatoEntity> contatoEntities = new ArrayList<>();
            for (ContatoDTO dto : contatos) {
                ContatoEntity entity = new ContatoEntity();
                entity.setContato(dto.getContato());
                entity.setFamiliar(familiar);
                contatoEntities.add(entity);
            }
            contatoRepository.saveAll(contatoEntities);

        }
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

        auditar(composicaoFamiliar.toString(),
                assistido.getIdResponsavelPeloCadastro(),
                AuditoriaEnum.CREATED.getValues());

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

            auditar(responsavelEntity.toString(),
                    assistido.getIdResponsavelPeloCadastro(),
                    AuditoriaEnum.CREATED.getValues());

        } else {
            entity.setNome(responsavelDTO.getNome());
            entity.setTipoParentesco(TipoParentesco.valueOf(responsavelDTO.getTipoParentesco()));
            entity.setAssistido(assistido);
            ResponsavelEntity responsavel = responsavelRepository.save(entity);
            persistirContatos(responsavelDTO, responsavel);

            auditar(entity.toString(),
                    assistido.getIdResponsavelPeloCadastro(),
                    AuditoriaEnum.UPDATED.getValues());

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

    private AssistidoEntity updateAssistido(AssistidoDTO assistidoDTO) {
        Optional<AssistidoEntity> optionalAssistido = assistidoRepository.findById(assistidoDTO.getId());
        if (optionalAssistido.isEmpty()){
            throw new NotFoundException("Assistido não encontrado.");
        }
        AssistidoEntity entity = optionalAssistido.get();
        entity.setId(assistidoDTO.getId());
        entity.setNome(assistidoDTO.getNome());
        entity.setDataNascimento(assistidoDTO.getDataNascimento());
        entity.setEscolaridade(assistidoDTO.getEscolaridade());
        entity.setEscola(assistidoDTO.getEscola());
        entity.setTelEscola(assistidoDTO.getTelEscola());
        entity.setCadastroInstituicao(assistidoDTO.isCadastroInstituicao());
        if(entity.isCadastroInstituicao() == true) {
            if(assistidoDTO.getInstituicao() == null || assistidoDTO.getInstituicao().isEmpty()){
                throw new BusinessException("O campo 'instituicao' em Assistido, é obrigatorio");
            }
        }
        entity.setInstituicao(assistidoDTO.getInstituicao());
        entity.setEncaminhadoPara(assistidoDTO.getEncaminhadoPara());
        entity.setQuemIndicouApajac(assistidoDTO.getQuemIndicouApajac());
        entity.setInformacoesFornecidasPor(assistidoDTO.getInformacoesFornecidasPor());
        entity.setEndereco(getEndereco(assistidoDTO.getEndereco()));
        entity.setObservacoes(assistidoDTO.getObservacoes());
        entity.setIdResponsavelPeloCadastro(assistidoDTO.getIdResponsavelPeloCadastro());
        entity.setCadastradoEm(assistidoDTO.getCadastradoEm());

        auditar(entity.toString(),
                assistidoDTO.getIdResponsavelPeloCadastro(),
                AuditoriaEnum.UPDATED.getValues());

        return assistidoRepository.save(entity);
    }

    private void updateFamiliares(List<FamiliarDTO> familiarDTOS, AssistidoEntity assistido) {
        List<FamiliarEntity> familiarEntities = familiarRepository.findByAssistido(assistido);
        if(!isNull(familiarEntities)){
            familiarRepository.deleteAll(familiarEntities);
        }

        for (FamiliarDTO familiarDTO : familiarDTOS) {

            // Verifica o tipo de parentesco
            if (!"PAI".equals(familiarDTO.getTipoParentesco())) {
                // Valida o nome
                if (familiarDTO.getNome() == null || familiarDTO.getNome().isEmpty()) {
                    throw new BusinessException(
                            String.format("O campo 'nome' em Familiar (%s), é obrigatório.", familiarDTO.getTipoParentesco())
                    );                }
                // Valida a lista de contatos
                if (familiarDTO.getContatos() == null || familiarDTO.getContatos().isEmpty()) {
                    throw new BusinessException(
                            String.format("A 'Lista de contatos' em Familiar (%s), é obrigatorio.", familiarDTO.getTipoParentesco())
                    );                 }
                // Valida os campos dentro de cada ContatoDTO
                for (ContatoDTO contato : familiarDTO.getContatos()) {
                    if (contato.getContato() == null || contato.getContato().isEmpty()) {
                        throw new BusinessException(
                                String.format("O campo 'contato' dentro da lista de contatos em Familiar (%s), é obrigatorio.", familiarDTO.getTipoParentesco())
                        );
                    }
                }
            }

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

        auditar(familiarDTOS.toString(),
                assistido.getIdResponsavelPeloCadastro(),
                AuditoriaEnum.UPDATED.getValues());
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

        auditar(composicaoFamiliarDTOS.toString(),
                assistido.getIdResponsavelPeloCadastro(),
                AuditoriaEnum.UPDATED.getValues());

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
    private void auditar(String body, Long idResponsavel, String tipo) {
        auditoria.inserirDadosDeAuditoria(
                idResponsavel,
                tipo,
                PersistirAssistidoService.class.getSimpleName(),
                body);
    }
}
