package com.apajac.acolhimento.controllers.assistidoController;

import com.apajac.acolhimento.services.interfaces.AtualizarStatusAssistidoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
class AtualizarStatusAssistidoControllerTest {

    @Mock
    private AtualizarStatusAssistidoService statusAssistidoService;

    @InjectMocks
    private AtualizarStatusAssistidoController statusAssistidoController;

    @Test
    void dadoUpdateStatusAssistido_entaoRetorneNoContent() {
        // Configuração dos parâmetros de entrada
        Long id = 1L;
        Long idResponsavel = 2L;

        // Configuração do comportamento esperado do serviço
        Mockito.doNothing().when(statusAssistidoService).updateStatusAssistido(id, idResponsavel);

        // Chama o método a ser testado
        ResponseEntity<String> response = statusAssistidoController.updateStatusAssistido(id, idResponsavel);

        // Assert
        // Verifica se o status da resposta e o corpo são os esperados
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals("Status alterado com sucesso!", response.getBody());
    }

    @Test
    void dadoUpdateStatusAssistidoComErro_entaoRetorneResponseStatusException() {
        // Configuração dos parâmetros de entrada
        Long id = 1L;
        Long idResponsavel = 2L;

        // Configuração do comportamento esperado do serviço para lançar uma exceção
        Mockito.doThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad Request"))
                .when(statusAssistidoService).updateStatusAssistido(id, idResponsavel);

        // Verifica se o método lança a exceção esperada
        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> {
            statusAssistidoController.updateStatusAssistido(id, idResponsavel);
        });

        // Verifica o status e a mensagem da exceção
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
        assertEquals("400 BAD_REQUEST \"Bad Request\"", thrown.getMessage());
    }
}