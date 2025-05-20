package controllers.usuarioController;

import com.apajac.acolhimento.controllers.usuario.BuscarUsuarioPorIdController;
import com.apajac.acolhimento.domain.dtos.UsuarioSemSenhaDTO;
import com.apajac.acolhimento.domain.entities.UsuarioEntity;
import com.apajac.acolhimento.mappers.UsuarioMapper;
import com.apajac.acolhimento.services.interfaces.UsuarioService;
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
class BuscarUsuarioPorIdControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private UsuarioMapper usuarioMapper;

    @InjectMocks
    private BuscarUsuarioPorIdController buscarUsuarioPorIdController;

    @Test
    void dadoIdValido_quandoBuscarUsuario_entaoRetorneUsuarioSemSenhaDTO() {
        // Configuração dos parâmetros de entrada
        Long id = 1L;

        // Configuração do comportamento esperado do serviço e do mapeador
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setId(id);
        usuarioEntity.setNome("Nome do Usuário");

        UsuarioSemSenhaDTO usuarioSemSenhaDTO = new UsuarioSemSenhaDTO();
        usuarioSemSenhaDTO.setId(id);
        usuarioSemSenhaDTO.setNome("Nome do Usuário");

        Mockito.when(usuarioService.buscarUsuarioPorId(id)).thenReturn(usuarioEntity);
        Mockito.when(usuarioMapper.convertEntityToDto(usuarioEntity)).thenReturn(usuarioSemSenhaDTO);

        // Chama o método a ser testado
        ResponseEntity<UsuarioSemSenhaDTO> response = buscarUsuarioPorIdController.buscarUsuario(id);

        // Assert
        // Verifica se o status da resposta é OK e se o corpo é o esperado
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(usuarioSemSenhaDTO, response.getBody());
    }

    @Test
    void dadoIdInvalido_quandoBuscarUsuario_entaoLancaHttpClientErrorException() {
        // Configuração dos parâmetros de entrada
        Long id = 1L;

        // Configuração do comportamento esperado do serviço para lançar uma exceção
        HttpClientErrorException exception = new HttpClientErrorException(HttpStatus.NOT_FOUND, "Usuário não encontrado");
        Mockito.when(usuarioService.buscarUsuarioPorId(id)).thenThrow(exception);

        // Verifica se o método lança a exceção esperada
        HttpClientErrorException thrown = assertThrows(HttpClientErrorException.class, () -> {
            buscarUsuarioPorIdController.buscarUsuario(id);
        });

        // Verifica o status e a mensagem da exceção
        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals("404 404 Usuário não encontrado", thrown.getMessage());
    }
}