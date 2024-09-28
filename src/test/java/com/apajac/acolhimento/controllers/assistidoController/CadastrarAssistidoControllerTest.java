package com.apajac.acolhimento.controllers.assistidoController;

import com.apajac.acolhimento.domain.dtos.AssistidoDTO;
import com.apajac.acolhimento.services.interfaces.PersistirAssistidoService;
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
class CadastrarAssistidoControllerTest {

    @Mock
    private PersistirAssistidoService assistidoService;

    @InjectMocks
    private CadastrarAssistidoController cadastrarAssistidoController;

    @Test
    void dadoCreateAssistidoNovo_entaoRetorneCreated() {
        // Criação de um modelo de entrada (DTO) sem ID (novo cadastro)
        AssistidoDTO assistidoDTO = new AssistidoDTO();
        assistidoDTO.setNome("Novo Assistido");

        // Configuração do comportamento esperado do serviço
        Mockito.doNothing().when(assistidoService).persistirAssistido(assistidoDTO);

        // Chama o método a ser testado
        ResponseEntity<String> response = cadastrarAssistidoController.createAssistido(assistidoDTO);

        // Assert
        // Verifica se o status da resposta e o corpo são os esperados
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Assistido cadastrado com sucesso!", response.getBody());
    }

    @Test
    void dadoCreateAssistidoExistente_entaoRetorneAccepted() {
        // Criação de um modelo de entrada (DTO) com ID (atualização)
        AssistidoDTO assistidoDTO = new AssistidoDTO();
        assistidoDTO.setId(1L);
        assistidoDTO.setNome("Assistido Existente");

        // Configuração do comportamento esperado do serviço
        Mockito.doNothing().when(assistidoService).persistirAssistido(assistidoDTO);

        // Chama o método a ser testado
        ResponseEntity<String> response = cadastrarAssistidoController.createAssistido(assistidoDTO);

        // Assert
        // Verifica se o status da resposta e o corpo são os esperados
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals("Assistido atualizado com sucesso!", response.getBody());
    }

    @Test
    void dadoCreateAssistidoComErro_entaoRetorneHttpClientErrorException() {
        // Criação de um modelo de entrada (DTO)
        AssistidoDTO assistidoDTO = new AssistidoDTO();
        assistidoDTO.setNome("Assistido com Erro");

        // Configuração do comportamento esperado do serviço para lançar uma exceção
        HttpClientErrorException exception = new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Erro ao persistir assistido");
        Mockito.doThrow(exception).when(assistidoService).persistirAssistido(assistidoDTO);

        // Verifica se o método lança a exceção esperada
        HttpClientErrorException thrown = assertThrows(HttpClientErrorException.class, () -> {
            cadastrarAssistidoController.createAssistido(assistidoDTO);
        });

        // Verifica o status e a mensagem da exceção
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
        assertEquals("400 Erro ao persistir assistido", thrown.getMessage());
    }
}
