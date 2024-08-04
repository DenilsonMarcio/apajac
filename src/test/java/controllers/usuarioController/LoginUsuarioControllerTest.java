package controllers.usuarioController;

import com.apajac.acolhimento.controllers.usuario.LoginUsuarioController;
import com.apajac.acolhimento.domain.dtos.LoginDTO;
import com.apajac.acolhimento.domain.dtos.UsuarioLogadoDTO;
import com.apajac.acolhimento.services.interfaces.LoginUsuarioService;
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
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class LoginUsuarioControllerTest {

    @Mock
    private LoginUsuarioService loginUsuarioService;

    @InjectMocks
    private LoginUsuarioController loginUsuarioController;

    @Test
    void dadoLoginDTOValido_quandoLoginUsuario_entaoRetorneUsuarioLogadoDTO() {
        // Configuração dos parâmetros de entrada
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setLogin("user");
        loginDTO.setPassword("password");

        // Configuração do resultado esperado
        UsuarioLogadoDTO usuarioLogado = new UsuarioLogadoDTO();
        usuarioLogado.setLogin("user");

        // Configuração do comportamento esperado do serviço
        when(loginUsuarioService.login(loginDTO)).thenReturn(usuarioLogado);

        // Chama o método a ser testado
        ResponseEntity<UsuarioLogadoDTO> response = loginUsuarioController.loginUsuario(loginDTO);

        // Assert
        // Verifica se o status da resposta é OK
        assertEquals(HttpStatus.OK, response.getStatusCode());
        // Verifica se o corpo da resposta é o esperado
        assertEquals(usuarioLogado, response.getBody());
    }

    @Test
    void dadoLoginDTOInvalido_quandoLoginUsuario_entaoLancaHttpClientErrorException() {
        // Configuração dos parâmetros de entrada
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setLogin("user");
        loginDTO.setPassword("wrongpassword");

        // Configuração do comportamento esperado do serviço para lançar uma exceção
        HttpClientErrorException exception = new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        when(loginUsuarioService.login(loginDTO)).thenThrow(exception);

        // Verifica se o método lança a exceção esperada
        HttpClientErrorException thrown = assertThrows(HttpClientErrorException.class, () -> {
            loginUsuarioController.loginUsuario(loginDTO);
        });

        // Verifica o status e a mensagem da exceção
        assertEquals(HttpStatus.UNAUTHORIZED, thrown.getStatusCode());
        assertEquals("401 Unauthorized", thrown.getMessage());
    }
}