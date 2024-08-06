//package services;
//
//import com.apajac.acolhimento.domain.dtos.*;
//import com.apajac.acolhimento.domain.entities.AssistidoEntity;
//import com.apajac.acolhimento.domain.entities.Endereco;
//import com.apajac.acolhimento.domain.entities.FamiliarEntity;
//import com.apajac.acolhimento.domain.entities.ResponsavelEntity;
//import com.apajac.acolhimento.domain.enums.TipoParentesco;
//import com.apajac.acolhimento.exceptions.BusinessException;
//import com.apajac.acolhimento.repositories.*;
//import com.apajac.acolhimento.services.PersistirAssistidoServiceImpl;
//import com.apajac.acolhimento.services.interfaces.AuditoriaService;
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.modelmapper.ModelMapper;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyList;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//@Transactional
//class PersistirAssistidoServiceImplTest {
//
//    @Mock
//    private AssistidoRepository assistidoRepository;
//
//    @Mock
//    private FamiliarRepository familiarRepository;
//
//    @Mock
//    private ComposicaoFamiliarRepository composicaoFamiliarRepository;
//
//    @Mock
//    private ContatoRepository contatoRepository;
//
//    @Mock
//    private ResponsavelRepository responsavelRepository;
//
//    @Mock
//    private AuditoriaService auditoria;
//
//    @Mock
//    private ModelMapper modelMapper;
//
//    @InjectMocks
//    private PersistirAssistidoServiceImpl persistirAssistidoService;
//
//    @Test
//    void persistirAssistido_DeveSalvarNovoAssistido() {
//        AssistidoDTO assistidoDTO = createAssistidoDTO();
//        AssistidoEntity assistidoEntity = modelMapper.map(assistidoDTO, AssistidoEntity.class);
//
//        when(assistidoRepository.save(any(AssistidoEntity.class))).thenReturn(assistidoEntity);
//        when(familiarRepository.save(any(FamiliarEntity.class))).thenReturn(new FamiliarEntity());
//        when(composicaoFamiliarRepository.saveAll(anyList())).thenReturn(Collections.emptyList());
//        when(responsavelRepository.save(any(ResponsavelEntity.class))).thenReturn(new ResponsavelEntity());
//        doNothing().when(auditoria).inserirDadosDeAuditoria(anyLong(),anyString(),anyString(),anyString());
//
//        persistirAssistidoService.persistirAssistido(assistidoDTO);
//
//        verify(assistidoRepository).save(any(AssistidoEntity.class));
//        verify(familiarRepository).save(any(FamiliarEntity.class));
//        verify(composicaoFamiliarRepository).saveAll(anyList());
//        verify(responsavelRepository).save(any(ResponsavelEntity.class));
//        verify(auditoria).inserirDadosDeAuditoria(any(), any(), any(), any());
//    }
//
//    @Test
//    void persistirAssistido_DeveAtualizarAssistidoExistente() {
//        AssistidoDTO assistidoDTO = createAssistidoDTO();
//        assistidoDTO.setId(1L);
//        AssistidoEntity assistidoEntity = createAssistidoEntity(assistidoDTO);
//
//        when(assistidoRepository.findById(assistidoDTO.getId())).thenReturn(Optional.of(assistidoEntity));
//        when(assistidoRepository.save(any(AssistidoEntity.class))).thenReturn(assistidoEntity);
//        when(familiarRepository.save(any(FamiliarEntity.class))).thenReturn(new FamiliarEntity());
//        when(composicaoFamiliarRepository.saveAll(anyList())).thenReturn(Collections.emptyList());
//        when(responsavelRepository.save(any(ResponsavelEntity.class))).thenReturn(new ResponsavelEntity());
//
//        persistirAssistidoService.persistirAssistido(assistidoDTO);
//
//        verify(assistidoRepository).save(any(AssistidoEntity.class));
//        verify(familiarRepository).save(any(FamiliarEntity.class));
//        verify(composicaoFamiliarRepository).saveAll(anyList());
//        verify(responsavelRepository).save(any(ResponsavelEntity.class));
//        verify(auditoria).inserirDadosDeAuditoria(any(), any(), any(), any());
//    }
//
//    @Test
//    void persistirAssistido_DeveLancarBusinessExceptionQuandoErroOcorre() {
//        AssistidoDTO assistidoDTO = createAssistidoDTO();
//        when(assistidoRepository.save(any(AssistidoEntity.class))).thenThrow(new RuntimeException("Erro ao salvar assistido"));
//
//        BusinessException exception = assertThrows(BusinessException.class, () -> persistirAssistidoService.persistirAssistido(assistidoDTO));
//        assertEquals("Não foi possivel inserir o Assistido: Erro ao salvar assistido", exception.getMessage());
//    }
//
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
////        assistidoEntity.setFamiliares();
////        assistidoEntity.setComposicaoFamiliar();
////        assistidoEntity.setResponsavel(assistidoDTO.getResponsavel());
//        assistidoEntity.setStatusAssistido(Boolean.TRUE);
//
//        return assistidoEntity;
//    }
//}