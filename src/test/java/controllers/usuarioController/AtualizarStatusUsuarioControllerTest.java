package controllers.usuarioController;

import com.apajac.acolhimento.controllers.usuario.AtualizarStatusUsuarioController;
import com.apajac.acolhimento.services.interfaces.AtualizarStatusUsuarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
class AtualizarStatusUsuarioControllerTest {

    @Mock
    private AtualizarStatusUsuarioService atualizarStatusUsuarioService;

    @InjectMocks
    private AtualizarStatusUsuarioController atualizarStatusUsuarioController;

    @Test
    void dadoIdEIdResponsavelValidos_quandoAtualizarStatusUsuario_entaoRetorneNoContent() {
        // Configuração dos parâmetros de entrada
        Long id = 1L;
        Long idResponsavel = 2L;

        // Configuração do comportamento esperado do serviço
        Mockito.doNothing().when(atualizarStatusUsuarioService).updateStatusUsuario(id, idResponsavel);

        // Chama o método a ser testado
        ResponseEntity<String> response = atualizarStatusUsuarioController.updateStatusUsuario(id, idResponsavel);

        // Assert
        // Verifica se o status da resposta é NO_CONTENT
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals("Status alterado com sucesso!", response.getBody());
    }

    @Test
    void dadoIdEIdResponsavelInvalidos_quandoAtualizarStatusUsuario_entaoLancaHttpClientErrorException() {
        // Configuração dos parâmetros de entrada
        Long id = 1L;
        Long idResponsavel = 2L;

        // Configuração do comportamento esperado do serviço para lançar uma exceção
        HttpClientErrorException exception = new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Dados inválidos");
        Mockito.doThrow(exception).when(atualizarStatusUsuarioService).updateStatusUsuario(id, idResponsavel);

        // Verifica se o método lança a exceção esperada
        HttpClientErrorException thrown = assertThrows(HttpClientErrorException.class, () -> {
            atualizarStatusUsuarioController.updateStatusUsuario(id, idResponsavel);
        });

        // Verifica o status e a mensagem da exceção
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
        assertEquals("400 Dados inválidos", thrown.getMessage());
    }
}
