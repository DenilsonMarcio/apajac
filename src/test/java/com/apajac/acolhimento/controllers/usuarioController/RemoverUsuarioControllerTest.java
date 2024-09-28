package com.apajac.acolhimento.controllers.usuarioController;

import com.apajac.acolhimento.controllers.usuario.RemoverUsuarioController;
import com.apajac.acolhimento.services.UsuarioServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class RemoverUsuarioControllerTest {

    @Mock
    private UsuarioServiceImpl usuarioService;

    @InjectMocks
    private RemoverUsuarioController removerUsuarioController;

    @Test
    void dadoIdValido_quandoRemover_entaoRetorneNoContent() {
        // Configuração dos parâmetros de entrada
        Long id = 1L;

        // Configuração do comportamento esperado do serviço
        doNothing().when(usuarioService).remover(id);

        // Chama o método a ser testado
        ResponseEntity<Void> response = removerUsuarioController.remover(id);

        // Assert
        // Verifica se o status da resposta é NO_CONTENT
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        // Verifica se o método remover do serviço foi chamado
        verify(usuarioService, times(1)).remover(id);
    }

    @Test
    void dadoHttpClientErrorException_quandoRemover_entaoLancaHttpClientErrorException() {
        // Configuração dos parâmetros de entrada
        Long id = 1L;

        // Configuração do comportamento esperado do serviço para lançar uma exceção
        HttpClientErrorException exception = new HttpClientErrorException(HttpStatus.NOT_FOUND, "Usuário não encontrado");
        doThrow(exception).when(usuarioService).remover(id);

        // Verifica se o método lança a exceção esperada
        HttpClientErrorException thrown = assertThrows(HttpClientErrorException.class, () -> {
            removerUsuarioController.remover(id);
        });

        // Verifica o status e a mensagem da exceção
        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals("404 404 Usuário não encontrado", thrown.getMessage());
    }
}
