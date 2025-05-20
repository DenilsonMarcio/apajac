package services;

import com.apajac.acolhimento.domain.dtos.*;
import com.apajac.acolhimento.domain.entities.*;
import com.apajac.acolhimento.domain.enums.TipoParentesco;
import com.apajac.acolhimento.exceptions.BusinessException;
import com.apajac.acolhimento.repositories.*;
import com.apajac.acolhimento.services.PersistirAssistidoServiceImpl;
import com.apajac.acolhimento.services.interfaces.AuditoriaService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Transactional
class PersistirAssistidoServiceImplTest {

    @Mock
    private AssistidoRepository assistidoRepository;

    @Mock
    private FamiliarRepository familiarRepository;

    @Mock
    private ComposicaoFamiliarRepository composicaoFamiliarRepository;

    @Mock
    private ContatoRepository contatoRepository;

    @Mock
    private ResponsavelRepository responsavelRepository;

    @Mock
    private AuditoriaService auditoria;

    @InjectMocks
    private PersistirAssistidoServiceImpl persistirAssistidoService;

    @Test
    void persistirAssistido_DeveSalvarNovoAssistido() {
        AssistidoDTO assistidoDTO = createAssistidoDTO();
        AssistidoEntity assistidoEntity = createAssistidoEntity(assistidoDTO);

        when(assistidoRepository.save(any(AssistidoEntity.class))).thenReturn(assistidoEntity);
        when(familiarRepository.save(any(FamiliarEntity.class))).thenReturn(new FamiliarEntity());
        when(composicaoFamiliarRepository.saveAll(anyList())).thenReturn(Collections.emptyList());
        when(responsavelRepository.save(any(ResponsavelEntity.class))).thenReturn(new ResponsavelEntity());
        doNothing().when(auditoria).inserirDadosDeAuditoria(anyLong(), anyString(), anyString(), anyString());

        persistirAssistidoService.persistirAssistido(assistidoDTO);

        verify(assistidoRepository).save(any(AssistidoEntity.class));
        verify(familiarRepository, times(assistidoDTO.getFamiliares().size())).save(any(FamiliarEntity.class));
        verify(composicaoFamiliarRepository).saveAll(anyList());
        verify(responsavelRepository).save(any(ResponsavelEntity.class));
    }

    @Test
    void persistirAssistido_DeveAtualizarAssistidoExistente() {
        AssistidoDTO assistidoDTO = createAssistidoDTO();
        assistidoDTO.setId(1L);
        AssistidoEntity assistidoEntity = createAssistidoEntity(assistidoDTO);

        when(assistidoRepository.findById(assistidoDTO.getId())).thenReturn(Optional.of(assistidoEntity));
        when(assistidoRepository.save(any(AssistidoEntity.class))).thenReturn(assistidoEntity);

        // Remover ou usar lenient() em stubs que não são necessários para este teste
        lenient().when(familiarRepository.save(any(FamiliarEntity.class))).thenReturn(new FamiliarEntity());
        lenient().when(composicaoFamiliarRepository.saveAll(anyList())).thenReturn(Collections.emptyList());
        lenient().when(responsavelRepository.save(any(ResponsavelEntity.class))).thenReturn(new ResponsavelEntity());

        persistirAssistidoService.persistirAssistido(assistidoDTO);

        verify(assistidoRepository).save(any(AssistidoEntity.class));
        verify(familiarRepository, times(assistidoDTO.getFamiliares().size())).save(any(FamiliarEntity.class));
        verify(responsavelRepository).save(any(ResponsavelEntity.class));
    }

    @Test
    void persistirAssistido_DeveLancarBusinessExceptionQuandoErroOcorre() {
        AssistidoDTO assistidoDTO = createAssistidoDTO();
        when(assistidoRepository.save(any(AssistidoEntity.class))).thenThrow(new RuntimeException("Erro ao salvar assistido"));

        BusinessException exception = assertThrows(BusinessException.class, () -> persistirAssistidoService.persistirAssistido(assistidoDTO));
        assertEquals("Não foi possivel inserir o Assistido: Erro ao salvar assistido", exception.getMessage());
    }

    @Test
    void persistirAssistido_DeveLancarNotFoundExceptionQuandoAssistidoNaoEncontrado() {
        AssistidoDTO assistidoDTO = createAssistidoDTO();
        assistidoDTO.setId(1L);

        when(assistidoRepository.findById(assistidoDTO.getId())).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () -> persistirAssistidoService.persistirAssistido(assistidoDTO));
        assertEquals("Não foi possivel inserir o Assistido: Assistido não encontrado.", exception.getMessage());
    }
//
//    @Test
//    void persistirAssistido_DeveLancarBusinessExceptionQuandoErroOcorre() {
//        AssistidoDTO assistidoDTO = createAssistidoDTO();
//        when(assistidoRepository.save(any(AssistidoEntity.class))).thenThrow(new RuntimeException("Erro ao salvar assistido"));
//
//        BusinessException exception = assertThrows(BusinessException.class, () -> persistirAssistidoService.persistirAssistido(assistidoDTO));
//        assertEquals("Não foi possivel inserir o Assistido: Erro ao salvar assistido", exception.getMessage());
//    }

    // Métodos auxiliares para criar objetos de DTO e Entity
    private AssistidoDTO createAssistidoDTO() {
        AssistidoDTO dto = new AssistidoDTO();
        dto.setNome("Assistido Teste");
        dto.setDataNascimento(LocalDate.now().minusYears(18));
        dto.setEscolaridade("Ensino Médio");
        dto.setEscola("Escola Teste");
        dto.setTelEscola("123456789");
        dto.setCadastroInstituicao(true);
        dto.setInstituicao("Instituição Teste");
        dto.setEncaminhadoPara("Encaminhamento Teste");
        dto.setQuemIndicouApajac("Indicação Teste");
        dto.setInformacoesFornecidasPor("Informante Teste");
        dto.setEndereco(new EnderecoDTO());
        dto.setObservacoes("Observações Teste");
        dto.setIdResponsavelPeloCadastro(1L);
        dto.setCadastradoEm(LocalDate.now());
        dto.setFamiliares(Collections.singletonList(createFamiliarDTO()));
        dto.setComposicaoFamiliar(Collections.singletonList(createComposicaoFamiliarDTO()));
        dto.setResponsavel(createResponsavelDTO());
        return dto;
    }

    private AssistidoEntity createAssistidoEntity(AssistidoDTO dto) {
        AssistidoEntity entity = new AssistidoEntity();
        entity.setNome(dto.getNome());
        entity.setDataNascimento(dto.getDataNascimento());
        entity.setEscolaridade(dto.getEscolaridade());
        entity.setEscola(dto.getEscola());
        entity.setTelEscola(dto.getTelEscola());
        entity.setCadastroInstituicao(dto.isCadastroInstituicao());
        entity.setInstituicao(dto.getInstituicao());
        entity.setEncaminhadoPara(dto.getEncaminhadoPara());
        entity.setQuemIndicouApajac(dto.getQuemIndicouApajac());
        entity.setInformacoesFornecidasPor(dto.getInformacoesFornecidasPor());
        entity.setEndereco(new Endereco());
        entity.setObservacoes(dto.getObservacoes());
        entity.setIdResponsavelPeloCadastro(dto.getIdResponsavelPeloCadastro());
        entity.setCadastradoEm(dto.getCadastradoEm());
        return entity;
    }

    private FamiliarDTO createFamiliarDTO() {
        FamiliarDTO familiarDTO = new FamiliarDTO();
        familiarDTO.setNome("Familiar Teste");
        familiarDTO.setOcupacao("Ocupação Teste");
        familiarDTO.setLocalTrabalho("Local Trabalho Teste");
        familiarDTO.setSalario(BigDecimal.valueOf(1000));
        familiarDTO.setVinculoEmpregaticio("Autonomo");
        familiarDTO.setTipoParentesco(TipoParentesco.OUTROS.name());
        familiarDTO.setContatos(Collections.singletonList(createContatoDTO()));
        return familiarDTO;
    }

    private ComposicaoFamiliarDTO createComposicaoFamiliarDTO() {
        ComposicaoFamiliarDTO dto = new ComposicaoFamiliarDTO();
        dto.setNome("Composição Teste");
        dto.setAnoNascimento(2005);
        dto.setParentesco("Primo");
        dto.setOcupacao("Estudante");
        dto.setObservacoes("Observações Teste");
        return dto;
    }

    private ResponsavelDTO createResponsavelDTO() {
        ResponsavelDTO dto = new ResponsavelDTO();
        dto.setNome("Responsável Teste");
        dto.setTipoParentesco(TipoParentesco.MAE.name());
        dto.setContatos(Collections.singletonList(createContatoDTO()));
        return dto;
    }

    private ContatoDTO createContatoDTO() {
        ContatoDTO dto = new ContatoDTO();
        dto.setContato("Contato Teste");
        return dto;
    }



//    private AssistidoDTO createAssistidoDTO() {
//        // Implementar a criação de um AssistidoDTO com dados de exemplo
//        return AssistidoDTO.builder()
//                .nome("Nome")
//                .dataNascimento(LocalDate.now())
//                .escolaridade("Escolaridade")
//                .escola("Escola")
//                .telEscola("123456789")
//                .cadastroInstituicao(Boolean.TRUE)
//                .instituicao("Instituição")
//                .encaminhadoPara("EncaminhadoPara")
//                .quemIndicouApajac("QuemIndicouApajac")
//                .informacoesFornecidasPor("InformaçõesFornecidasPor")
//                .endereco(EnderecoDTO.builder()
//                        .cep("123456")
//                        .endereco("teste")
//                        .numero("123")
//                        .bairro("teste")
//                        .cidade("teste")
//                        .UF("SP")
//                        .complemento("teste")
//                        .build())
//                .observacoes("Observações")
//                .idResponsavelPeloCadastro(1L)
//                .cadastradoEm(LocalDate.now())
//                .familiares(List.of(FamiliarDTO.builder()
//                                .nome("TESTE")
//                                .contatos(List.of(new ContatoDTO("123456879")))
//                                .ocupacao("teste")
//                                .localTrabalho("teste")
//                                .salario(BigDecimal.valueOf(5000))
//                                .vinculoEmpregaticio("teste")
//                                .tipoParentesco(TipoParentesco.MAE.name())
//                        .build()))
//                .composicaoFamiliar(List.of(ComposicaoFamiliarDTO.builder()
//                                .nome("Teste")
//                                .anoNascimento(1985)
//                                .parentesco("Parentesco")
//                                .ocupacao("teste")
//                                .observacoes("teste")
//                        .build()))
//                .responsavel(ResponsavelDTO.builder()
//                        .nome("teste")
//                        .tipoParentesco("Parentesco")
//                        .contatos(List.of(new ContatoDTO("123456879")))
//                        .build())
//                .statusAssistido(Boolean.TRUE)
//                .build();
//    }
//
//    private AssistidoEntity createAssistidoEntity(AssistidoDTO assistidoDTO) {
//        // Implementar a criação de um AssistidoEntity com dados de assistidoDTO
//        AssistidoEntity assistidoEntity = new AssistidoEntity();
//        assistidoEntity.setId(assistidoDTO.getId());
//        assistidoEntity.setNome(assistidoDTO.getNome());
//        assistidoEntity.setDataNascimento(assistidoDTO.getDataNascimento());
//        assistidoEntity.setEscolaridade(assistidoDTO.getEscolaridade());
//        assistidoEntity.setEscola(assistidoDTO.getEscola());
//        assistidoEntity.setTelEscola(assistidoDTO.getTelEscola());
//        assistidoEntity.setCadastroInstituicao(Boolean.TRUE);
//        assistidoEntity.setInstituicao(assistidoDTO.getInstituicao());
//        assistidoEntity.setEncaminhadoPara(assistidoDTO.getEncaminhadoPara());
//        assistidoEntity.setQuemIndicouApajac(assistidoDTO.getQuemIndicouApajac());
//        assistidoEntity.setInformacoesFornecidasPor(assistidoDTO.getInformacoesFornecidasPor());
//        assistidoEntity.setIdResponsavelPeloCadastro(assistidoDTO.getIdResponsavelPeloCadastro());
//        assistidoEntity.setCadastradoEm(assistidoDTO.getCadastradoEm());
//        assistidoEntity.setEndereco(new Endereco());
//        assistidoEntity.setObservacoes(assistidoDTO.getObservacoes());
//        assistidoEntity.setFamiliares(List.of(new FamiliarEntity()));
//        assistidoEntity.setComposicaoFamiliar(List.of(new ComposicaoFamiliarEntity()));
//        assistidoEntity.setResponsavel(new ResponsavelEntity());
//        assistidoEntity.setStatusAssistido(Boolean.TRUE);
//
//        return assistidoEntity;
//    }
}