package services;

import com.apajac.acolhimento.domain.entities.AssistidoEntity;
import com.apajac.acolhimento.exceptions.NotFoundException;
import com.apajac.acolhimento.repositories.AssistidoRepository;
import com.apajac.acolhimento.services.ConsultarAssistidoServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConsultarAssistidoServiceImplTest {

    @Mock
    private AssistidoRepository assistidoRepository;

    @InjectMocks
    private ConsultarAssistidoServiceImpl consultarAssistidoService;

    @Test
    void listarAssistidos_DeveRetornarListaDeAssistidos() {
        // Dados de entrada
        Pageable pageable = Pageable.unpaged();
        AssistidoEntity assistidoEntity = new AssistidoEntity();
        assistidoEntity.setId(1L);
        assistidoEntity.setNome("João");

        Page<AssistidoEntity> assistidosPage = new PageImpl<>(Collections.singletonList(assistidoEntity), pageable, 1);

        // Configuração do comportamento esperado do repositório
        when(assistidoRepository.findAll(pageable)).thenReturn(assistidosPage);

        // Chama o método a ser testado
        Page<AssistidoEntity> result = consultarAssistidoService.listarAssistidos(pageable);

        // Verificações
        assertEquals(1, result.getTotalElements());
        assertEquals(assistidoEntity, result.getContent().get(0));
    }

    @Test
    void buscarAssistidoPorId_DeveRetornarAssistidoSeExistir() {
        // Dados de entrada
        Long assistidoId = 1L;
        AssistidoEntity assistidoEntity = new AssistidoEntity();
        assistidoEntity.setId(assistidoId);
        assistidoEntity.setNome("João");

        // Configuração do comportamento esperado do repositório
        when(assistidoRepository.findById(assistidoId)).thenReturn(Optional.of(assistidoEntity));

        // Chama o método a ser testado
        AssistidoEntity result = consultarAssistidoService.buscarAssistidoPorId(assistidoId);

        // Verificações
        assertEquals(assistidoEntity, result);
    }

    @Test
    void buscarAssistidoPorId_DeveLancarNotFoundExceptionQuandoAssistidoNaoExistir() {
        // Dados de entrada
        Long assistidoId = 1L;

        // Configuração do comportamento esperado do repositório
        when(assistidoRepository.findById(assistidoId)).thenReturn(Optional.empty());

        // Verifica se o método lança a exceção esperada
        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                consultarAssistidoService.buscarAssistidoPorId(assistidoId));

        // Verifica se a exceção contém a mensagem correta
        assertEquals("Assistido não encontrado.", exception.getMessage());
    }

    @Test
    void buscarAssistidosPorNome_DeveRetornarListaDeAssistidosPorNome() {
        // Dados de entrada
        String nome = "João";
        Pageable pageable = Pageable.unpaged();
        AssistidoEntity assistidoEntity = new AssistidoEntity();
        assistidoEntity.setId(1L);
        assistidoEntity.setNome(nome);

        Page<AssistidoEntity> assistidosPage = new PageImpl<>(Collections.singletonList(assistidoEntity), pageable, 1);

        // Configuração do comportamento esperado do repositório
        when(assistidoRepository.findAllByNomeContainingIgnoreCase(anyString(), any(Pageable.class)))
                .thenReturn(assistidosPage);

        // Chama o método a ser testado
        Page<AssistidoEntity> result = consultarAssistidoService.buscarAssistidosPorNome(nome, pageable);

        // Verificações
        assertEquals(1, result.getTotalElements());
        assertEquals(assistidoEntity, result.getContent().get(0));
    }
}