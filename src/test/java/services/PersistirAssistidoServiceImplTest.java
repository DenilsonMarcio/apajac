package services;

import com.apajac.acolhimento.domain.dtos.AssistidoDTO;
import com.apajac.acolhimento.domain.dtos.EnderecoDTO;
import com.apajac.acolhimento.domain.dtos.ResponsavelDTO;
import com.apajac.acolhimento.domain.entities.AssistidoEntity;
import com.apajac.acolhimento.domain.entities.FamiliarEntity;
import com.apajac.acolhimento.domain.entities.ResponsavelEntity;
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

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        AssistidoEntity assistidoEntity = new AssistidoEntity();
        assistidoEntity.setNome(assistidoDTO.getNome());
        when(assistidoRepository.save(any(AssistidoEntity.class))).thenReturn(assistidoEntity);
        when(familiarRepository.save(any(FamiliarEntity.class))).thenReturn(new FamiliarEntity());
        when(composicaoFamiliarRepository.saveAll(anyList())).thenReturn(Collections.emptyList());
        when(responsavelRepository.save(any(ResponsavelEntity.class))).thenReturn(new ResponsavelEntity());

        persistirAssistidoService.persistirAssistido(assistidoDTO);

        verify(assistidoRepository).save(any(AssistidoEntity.class));
        verify(familiarRepository).save(any(FamiliarEntity.class));
        verify(composicaoFamiliarRepository).saveAll(anyList());
        verify(responsavelRepository).save(any(ResponsavelEntity.class));
        verify(auditoria).inserirDadosDeAuditoria(any(), any(), any(), any());
    }

    @Test
    void persistirAssistido_DeveAtualizarAssistidoExistente() {
        AssistidoDTO assistidoDTO = createAssistidoDTO();
        assistidoDTO.setId(1L);
        AssistidoEntity assistidoEntity = new AssistidoEntity();
        when(assistidoRepository.findById(assistidoDTO.getId())).thenReturn(Optional.of(assistidoEntity));
        when(assistidoRepository.save(any(AssistidoEntity.class))).thenReturn(assistidoEntity);
        when(familiarRepository.save(any(FamiliarEntity.class))).thenReturn(new FamiliarEntity());
        when(composicaoFamiliarRepository.saveAll(anyList())).thenReturn(Collections.emptyList());
        when(responsavelRepository.save(any(ResponsavelEntity.class))).thenReturn(new ResponsavelEntity());

        persistirAssistidoService.persistirAssistido(assistidoDTO);

        verify(assistidoRepository).save(any(AssistidoEntity.class));
        verify(familiarRepository).save(any(FamiliarEntity.class));
        verify(composicaoFamiliarRepository).saveAll(anyList());
        verify(responsavelRepository).save(any(ResponsavelEntity.class));
        verify(auditoria).inserirDadosDeAuditoria(any(), any(), any(), any());
    }

    @Test
    void persistirAssistido_DeveLancarBusinessExceptionQuandoErroOcorre() {
        AssistidoDTO assistidoDTO = createAssistidoDTO();
        when(assistidoRepository.save(any(AssistidoEntity.class))).thenThrow(new RuntimeException("Erro ao salvar assistido"));

        BusinessException exception = assertThrows(BusinessException.class, () -> persistirAssistidoService.persistirAssistido(assistidoDTO));
        assertEquals("Não foi possivel inserir o Assistido: Erro ao salvar assistido", exception.getMessage());
    }

    private AssistidoDTO createAssistidoDTO() {
        // Implementar a criação de um AssistidoDTO com dados de exemplo
        return AssistidoDTO.builder()
                .nome("Nome")
                .dataNascimento(LocalDate.now())
                .escolaridade("Escolaridade")
                .escola("Escola")
                .telEscola("123456789")
                .cadastroInstituicao(true)
                .instituicao("Instituição")
                .encaminhadoPara("EncaminhadoPara")
                .quemIndicouApajac("QuemIndicouApajac")
                .informacoesFornecidasPor("InformaçõesFornecidasPor")
                .endereco(new EnderecoDTO())
                .observacoes("Observações")
                .idResponsavelPeloCadastro(1L)
                .cadastradoEm(LocalDate.now())
                .familiares(Collections.emptyList())
                .composicaoFamiliar(Collections.emptyList())
                .responsavel(new ResponsavelDTO())
                .build();
    }
}