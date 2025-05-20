package controllers.usuarioController;

import com.apajac.acolhimento.controllers.usuario.AtualizarDadosUsuarioController;
import com.apajac.acolhimento.domain.dtos.UsuarioDTO;
import com.apajac.acolhimento.services.interfaces.AtualizarDadosUsuarioService;
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
class AtualizarDadosUsuarioControllerTest {

    @Mock
    private AtualizarDadosUsuarioService atualizarDadosUsuarioService;

    @InjectMocks
    private AtualizarDadosUsuarioController atualizarDadosUsuarioController;

    @Test
    void dadoIdEUsuarioDTOValidos_quandoAtualizarDadosUsuario_entaoRetorneNoContent() {
        // Configuração dos parâmetros de entrada
        Long id = 1L;
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome("Nome Atualizado");

        // Configuração do comportamento esperado do serviço
        Mockito.doNothing().when(atualizarDadosUsuarioService).updateDadosUsuario(id, usuarioDTO);

        // Chama o método a ser testado
        ResponseEntity<String> response = atualizarDadosUsuarioController.updateStatusUsuario(id, usuarioDTO);

        // Assert
        // Verifica se o status da resposta é NO_CONTENT
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals("Status alterado com sucesso!", response.getBody());
    }

    @Test
    void dadoIdEUsuarioDTOInvalidos_quandoAtualizarDadosUsuario_entaoLancaHttpClientErrorException() {
        // Configuração dos parâmetros de entrada
        Long id = 1L;
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome("Nome Atualizado");

        // Configuração do comportamento esperado do serviço para lançar uma exceção
        HttpClientErrorException exception = new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Dados inválidos");
        Mockito.doThrow(exception).when(atualizarDadosUsuarioService).updateDadosUsuario(id, usuarioDTO);

        // Verifica se o método lança a exceção esperada
        HttpClientErrorException thrown = assertThrows(HttpClientErrorException.class, () -> {
            atualizarDadosUsuarioController.updateStatusUsuario(id, usuarioDTO);
        });

        // Verifica o status e a mensagem da exceção
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
        assertEquals("400 Dados inválidos", thrown.getMessage());
    }
}
