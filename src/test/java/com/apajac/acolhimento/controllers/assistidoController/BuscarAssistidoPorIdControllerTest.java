package com.apajac.acolhimento.controllers.assistidoController;

import com.apajac.acolhimento.domain.dtos.AssistidoDTO;
import com.apajac.acolhimento.domain.entities.AssistidoEntity;
import com.apajac.acolhimento.mappers.AssistidoMapper;
import com.apajac.acolhimento.services.interfaces.ConsultarAssistidoService;
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
class BuscarAssistidoPorIdControllerTest {

    @Mock
    private ConsultarAssistidoService assistidoService;

    @Mock
    private AssistidoMapper assistidoMapper;

    @InjectMocks
    private BuscarAssistidoPorIdController buscarAssistidoPorIdController;

    @Test
    void dadoBuscarAssistidoPorId_entaoRetorneAssistidoDTO() {
        // Configuração do parâmetro de entrada
        Long id = 1L;

        // Criação de um modelo de entidade e um modelo de DTO
        AssistidoEntity assistidoEntity = new AssistidoEntity();
        assistidoEntity.setId(id);
        assistidoEntity.setNome("Assistido 1");

        AssistidoDTO assistidoDTO = new AssistidoDTO();
        assistidoDTO.setId(id);
        assistidoDTO.setNome("Assistido 1");

        // Configuração do comportamento esperado do serviço e do mapper
        Mockito.when(assistidoService.buscarAssistidoPorId(id)).thenReturn(assistidoEntity);
        Mockito.when(assistidoMapper.convertEntityToDto(assistidoEntity)).thenReturn(assistidoDTO);

        // Chama o método a ser testado
        ResponseEntity<AssistidoDTO> response = buscarAssistidoPorIdController.buscarAssistido(id);

        // Assert
        // Verifica se o status da resposta e o corpo são os esperados
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(assistidoDTO, response.getBody());
    }

    @Test
    void dadoBuscarAssistidoPorIdComErro_entaoRetorneHttpClientErrorException() {
        // Configuração do parâmetro de entrada
        Long id = 1L;

        // Configuração do comportamento esperado do serviço para lançar uma exceção
        HttpClientErrorException exception = new HttpClientErrorException(HttpStatus.NOT_FOUND, "Assistido não encontrado");
        Mockito.when(assistidoService.buscarAssistidoPorId(id)).thenThrow(exception);

        // Verifica se o método lança a exceção esperada
        HttpClientErrorException thrown = assertThrows(HttpClientErrorException.class, () -> {
            buscarAssistidoPorIdController.buscarAssistido(id);
        });

        // Verifica o status e a mensagem da exceção
        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals("404 404 Assistido não encontrado", thrown.getMessage());
    }
}
