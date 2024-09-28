package com.apajac.acolhimento.services;

import com.apajac.acolhimento.domain.entities.AssistidoEntity;
import com.apajac.acolhimento.domain.enums.AuditoriaEnum;
import com.apajac.acolhimento.exceptions.NotFoundException;
import com.apajac.acolhimento.repositories.AssistidoRepository;
import com.apajac.acolhimento.services.interfaces.AtualizarStatusAssistidoService;
import com.apajac.acolhimento.services.interfaces.AuditoriaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
class AtualizarStatusAssistidoServiceImplTest {

    @Mock
    private AssistidoRepository assistidoRepository;

    @Mock
    private AuditoriaService auditoria;

    @InjectMocks
    private AtualizarStatusAssistidoServiceImpl atualizarStatusAssistidoService;

    @Test
    void updateStatusAssistido_DeveAtualizarStatusDoAssistido() {
        // Dados de entrada
        Long assistidoId = 1L;
        Long responsavelId = 2L;

        AssistidoEntity assistidoEntity = new AssistidoEntity();
        assistidoEntity.setId(assistidoId);
        assistidoEntity.setStatusAssistido(false);

        // Configuração do comportamento esperado do repositório
        Mockito.when(assistidoRepository.findById(assistidoId)).thenReturn(Optional.of(assistidoEntity));
        Mockito.when(assistidoRepository.save(any(AssistidoEntity.class))).thenReturn(assistidoEntity);
        Mockito.doNothing().when(auditoria).inserirDadosDeAuditoria(any(Long.class), any(String.class), any(String.class), any(String.class));

        // Chama o método a ser testado
        atualizarStatusAssistidoService.updateStatusAssistido(assistidoId, responsavelId);

        // Verificações
        assertTrue(assistidoEntity.isStatusAssistido());
        Mockito.verify(assistidoRepository).save(assistidoEntity);
        Mockito.verify(auditoria).inserirDadosDeAuditoria(
                responsavelId,
                AuditoriaEnum.UPDATED.getValues(),
                AtualizarStatusAssistidoService.class.getSimpleName(),
                assistidoId.toString()
        );
    }

    @Test
    void updateStatusAssistido_DeveLancarNotFoundExceptionQuandoAssistidoNaoExistir() {
        // Dados de entrada
        Long assistidoId = 1L;
        Long responsavelId = 2L;

        // Configuração do comportamento esperado do repositório
        Mockito.when(assistidoRepository.findById(assistidoId)).thenReturn(Optional.empty());

        // Verifica se o método lança a exceção esperada
        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                atualizarStatusAssistidoService.updateStatusAssistido(assistidoId, responsavelId));

        // Verifica se a exceção contém a mensagem correta
        assertEquals("Assistido não encontrado.", exception.getMessage());
    }

    @Test
    void updateStatusAssistido_DeveChamarAuditoria() {
        // Dados de entrada
        Long assistidoId = 1L;
        Long responsavelId = 2L;

        AssistidoEntity assistidoEntity = new AssistidoEntity();
        assistidoEntity.setId(assistidoId);
        assistidoEntity.setStatusAssistido(false);

        // Configuração do comportamento esperado do repositório
        Mockito.when(assistidoRepository.findById(assistidoId)).thenReturn(Optional.of(assistidoEntity));
        Mockito.when(assistidoRepository.save(any(AssistidoEntity.class))).thenReturn(assistidoEntity);
        Mockito.doNothing().when(auditoria).inserirDadosDeAuditoria(any(Long.class), any(String.class), any(String.class), any(String.class));

        // Chama o método a ser testado
        atualizarStatusAssistidoService.updateStatusAssistido(assistidoId, responsavelId);

        // Verificações
        Mockito.verify(auditoria).inserirDadosDeAuditoria(
                responsavelId,
                AuditoriaEnum.UPDATED.getValues(),
                AtualizarStatusAssistidoService.class.getSimpleName(),
                assistidoId.toString()
        );
    }
}