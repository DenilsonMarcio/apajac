package controllers.usuarioController;

import com.apajac.acolhimento.controllers.usuario.CadastrarUsuarioController;
import com.apajac.acolhimento.domain.dtos.UsuarioDTO;
import com.apajac.acolhimento.exceptions.BusinessException;
import com.apajac.acolhimento.services.UsuarioServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.BindingResult;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class CadastrarUsuarioControllerTest {

    @Mock
    private UsuarioServiceImpl usuarioService;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private CadastrarUsuarioController cadastrarUsuarioController;

    @Test
    void dadoUsuarioDTOValido_quandoInserir_entaoRetorneCreated() {
        // Configuração dos parâmetros de entrada
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome("Nome do Usuário");

        // Configuração do comportamento do BindingResult
        when(bindingResult.hasErrors()).thenReturn(false);

        // Chama o método a ser testado
        ResponseEntity<String> response = cadastrarUsuarioController.inserir(usuarioDTO, bindingResult);

        // Assert
        // Verifica se o status da resposta é CREATED
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // Verifica se o método cadastrar do serviço foi chamado
        verify(usuarioService, times(1)).cadastrar(usuarioDTO);
    }

    @Test
    void dadoUsuarioDTOInvalido_quandoInserir_entaoLancaRuntimeException() {
        // Configuração dos parâmetros de entrada
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome("Nome do Usuário");

        // Configuração do comportamento do BindingResult
        when(bindingResult.hasErrors()).thenReturn(true);

        // Verifica se o método lança a exceção esperada
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            cadastrarUsuarioController.inserir(usuarioDTO, bindingResult);
        });

        // Verifica a mensagem da exceção
        assertEquals("Falha ao cadastrar o Usuário.", thrown.getMessage());

        // Verifica se o método cadastrar do serviço não foi chamado
        verify(usuarioService, times(0)).cadastrar(usuarioDTO);
    }

    @Test
    void dadoBusinessException_quandoInserir_entaoLancaBusinessException() {
        // Configuração dos parâmetros de entrada
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome("Nome do Usuário");

        // Configuração do comportamento do BindingResult
        when(bindingResult.hasErrors()).thenReturn(false);

        // Configuração do comportamento esperado do serviço para lançar uma exceção BusinessException
        doThrow(new BusinessException("Erro de negócio")).when(usuarioService).cadastrar(usuarioDTO);

        // Verifica se o método lança a exceção esperada
        BusinessException thrown = assertThrows(BusinessException.class, () -> {
            cadastrarUsuarioController.inserir(usuarioDTO, bindingResult);
        });

        // Verifica a mensagem da exceção
        assertEquals("Erro de negócio", thrown.getMessage());
    }

    @Test
    void dadoHttpClientErrorException_quandoInserir_entaoLancaHttpClientErrorException() {
        // Configuração dos parâmetros de entrada
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome("Nome do Usuário");

        // Configuração do comportamento do BindingResult
        when(bindingResult.hasErrors()).thenReturn(false);

        // Configuração do comportamento esperado do serviço para lançar uma exceção HttpClientErrorException
        HttpClientErrorException exception = new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Erro de cliente");
        doThrow(exception).when(usuarioService).cadastrar(usuarioDTO);

        // Verifica se o método lança a exceção esperada
        HttpClientErrorException thrown = assertThrows(HttpClientErrorException.class, () -> {
            cadastrarUsuarioController.inserir(usuarioDTO, bindingResult);
        });

        // Verifica o status e a mensagem da exceção
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
        assertEquals("400 400 Erro de cliente", thrown.getMessage());
    }
}
