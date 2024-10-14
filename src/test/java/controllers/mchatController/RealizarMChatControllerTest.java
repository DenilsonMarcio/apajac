package controllers.mchatController;

import com.apajac.acolhimento.controllers.mchatController.RealizarMChatController;
import com.apajac.acolhimento.domain.dtos.MChatDTO;
import com.apajac.acolhimento.domain.dtos.RespostaMChatDTO;
import com.apajac.acolhimento.services.interfaces.RealizarMChatAssistidoService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@Transactional
class RealizarMChatControllerTest {

    @Mock
    private RealizarMChatAssistidoService realizarMChatAssistidoService;

    @InjectMocks
    private RealizarMChatController realizarMChatController;

    //Teste do cadastro

    @Test
    void realizarMChatAssistidoTest_DeveCadastrarMChat() {
        // Criação de um modelo de entrada (DTO) sem ID (novo cadastro)
        MChatDTO mChatDTO = createMChatDTO();

        // Configuração do comportamento esperado do serviço
        Mockito.doNothing().when(realizarMChatAssistidoService).realizarMChatAssistido(mChatDTO);

        // Chama o método a ser testado
        ResponseEntity<String> response = realizarMChatController.realizarMChatAssistido(mChatDTO);

        // Verifica se o status da resposta e o corpo são os esperados
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("M-Chat realizado com sucesso.", response.getBody());
    }

    //Teste de ID Nulo

    @Test
    void RealizarMChatAssistido_DeveLancarExceptionQuandoIDNulo() {
        // Criação de um modelo de entrada (DTO)
        MChatDTO mChatDTO = createMChatDTO();

        // Configuração do comportamento esperado do serviço para lançar uma exceção
        HttpClientErrorException exception = new HttpClientErrorException(HttpStatus.NOT_ACCEPTABLE, "MChat precisa ser associado a um assistido");
        Mockito.doThrow(exception).when(realizarMChatAssistidoService).realizarMChatAssistido(mChatDTO);

        // Verifica se o método lança a exceção esperada
        HttpClientErrorException thrown = assertThrows(HttpClientErrorException.class, () ->
                realizarMChatController.realizarMChatAssistido(mChatDTO));

        // Verifica o status e a mensagem da exceção
        assertEquals(HttpStatus.NOT_ACCEPTABLE, thrown.getStatusCode());
        assertEquals("406 406 MChat precisa ser associado a um assistido", thrown.getMessage());
    }

    //Teste de 404

    @Test
    void RealizarMChatAssistido_DeveLancarExceptionQuandoNaoEncontrarAssistido() {
        // Criação de um modelo de entrada (DTO)
        MChatDTO mChatDTO = createMChatDTO();

        // Configuração do comportamento esperado do serviço para lançar uma exceção
        HttpClientErrorException exception = new HttpClientErrorException(HttpStatus.NOT_FOUND, "Assistido não encontrado!");
        Mockito.doThrow(exception).when(realizarMChatAssistidoService).realizarMChatAssistido(mChatDTO);

        // Verifica se o método lança a exceção esperada
        HttpClientErrorException thrown = assertThrows(HttpClientErrorException.class, () ->
                realizarMChatController.realizarMChatAssistido(mChatDTO));

        // Verifica o status e a mensagem da exceção
        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals("404 404 Assistido não encontrado!", thrown.getMessage());
    }

    //Gerador dinâmico de DTO

    private MChatDTO createMChatDTO() {
        MChatDTO mChatDTO = new MChatDTO();
        List<RespostaMChatDTO> respostaMChatDTOS = new ArrayList<>();
        mChatDTO.setId(1L);
        int soma = 0;
        for (int i = 1; i <= 20; i++) {
            Random random = new Random();
            RespostaMChatDTO respostaMChatDTO = new RespostaMChatDTO();
            respostaMChatDTO.setPergunta(i);
            respostaMChatDTO.setResposta(random.nextBoolean());
            respostaMChatDTOS.add(respostaMChatDTO);
            if (respostaMChatDTO.getResposta()) {
                soma++;
            }
        }
        if (soma > 10) {
            mChatDTO.setPea(true);
        }
        mChatDTO.setMchat(respostaMChatDTOS);
        return mChatDTO;
    }
}