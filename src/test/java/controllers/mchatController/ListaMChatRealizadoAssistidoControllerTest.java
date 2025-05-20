package controllers.mchatController;

import com.apajac.acolhimento.controllers.mchatController.ListaMChatRealizadoAssistidoController;
import com.apajac.acolhimento.domain.dtos.NomeAssistidoMChatDTO;
import com.apajac.acolhimento.services.interfaces.ListarMChatService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ListaMChatRealizadoAssistidoControllerTest {

    @InjectMocks
    ListaMChatRealizadoAssistidoController listaMChatRealizadoAssistidoController;

    @Mock
    ListarMChatService listarMChatService;

    @Test
    void deveRetornarMChatPorAssistidoComSucesso() {

        NomeAssistidoMChatDTO response = mock(NomeAssistidoMChatDTO.class);

        when(listarMChatService.listarMChatPorAssistido(1L)).thenReturn(response);

        ResponseEntity<NomeAssistidoMChatDTO> resultado = listaMChatRealizadoAssistidoController.listarMChatPorAssistido(1L);

        //verifica se o código de status HTTP da resposta retornada pelo controller é 200 OK
        assertEquals(HttpStatus.OK, resultado.getStatusCode(), "O código de status não está correto.");

        //Verifica se o corpo da resposta é igual ao objeto mockado.
        assertEquals(response, resultado.getBody(), "O corpo da resposta não está correto.");

        //Verifica se o metodo listarMChatPorAssistido() foi chamado corretamente
        verify(listarMChatService).listarMChatPorAssistido(1L);

    }

    @Test
    void ListaMChatRealizadoAssistidoController() {

        when(listarMChatService.listarMChatPorAssistido(1l))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class,
                () -> listarMChatService.listarMChatPorAssistido(1l));

        //verifica se o código de status HTTP da excecao lançada
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());

        //verifica se a mensagem da exceção esta certa
        assertEquals("404 NOT_FOUND", exception.getMessage() );
    }


}
