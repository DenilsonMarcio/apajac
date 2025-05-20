package controllers.assistidoController;

import com.apajac.acolhimento.controllers.assistidoController.ListaAssistidosPorNomeController;
import com.apajac.acolhimento.domain.dtos.AssistidoSimplificadoDTO;
import com.apajac.acolhimento.domain.dtos.ListaAssistidoDTO;
import com.apajac.acolhimento.domain.entities.AssistidoEntity;
import com.apajac.acolhimento.mappers.AssistidoMapper;
import com.apajac.acolhimento.services.interfaces.ConsultarAssistidoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class ListaAssistidosPorNomeControllerTest {

    @Mock
    private ConsultarAssistidoService assistidoService;

    @Mock
    private AssistidoMapper assistidoMapper;

    @InjectMocks
    private ListaAssistidosPorNomeController listaAssistidosPorNomeController;

    @Test
    void dadoBuscarAssistidosPorNome_entaoRetorneListaAssistidoDTO() {
        // Configuração dos parâmetros de entrada
        String nome = "Assistido";
        Pageable pageable = PageRequest.of(1, 10);

        // Criação de um modelo de entidade e um modelo de DTO
        AssistidoEntity assistidoEntity = new AssistidoEntity();
        assistidoEntity.setNome("Assistido 1");

        List<AssistidoEntity> assistidoEntities = Collections.singletonList(assistidoEntity);
        Page<AssistidoEntity> assistidosPage = new PageImpl<>(assistidoEntities, pageable, 1);

        AssistidoSimplificadoDTO assistidoDTO = new AssistidoSimplificadoDTO();
        assistidoDTO.setNome("Assistido 1");

        List<AssistidoSimplificadoDTO> assistidoDTOs = Collections.singletonList(assistidoDTO);

        // Configuração do comportamento esperado do serviço e do mapper
        Mockito.when(assistidoService.buscarAssistidosPorNome(nome, pageable)).thenReturn(assistidosPage);
        Mockito.when(assistidoMapper.convertEntitesToDtos(assistidoEntities, pageable.getSort().toString())).thenReturn(assistidoDTOs);

        // Chama o método a ser testado
        ResponseEntity<ListaAssistidoDTO> response = listaAssistidosPorNomeController.buscarAssistidosPorNome(nome, pageable);

        // Assert
        // Verifica se o status da resposta e o corpo são os esperados
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(assistidoDTOs, response.getBody().getAssistidos());
        assertTrue(response.getBody().getIsLastPage());
    }

    @Test
    void dadoBuscarAssistidosPorNomeComErro_entaoRetorneHttpClientErrorException() {
        // Configuração dos parâmetros de entrada
        String nome = "Assistido";
        Pageable pageable = PageRequest.of(0, 10);

        // Configuração do comportamento esperado do serviço para lançar uma exceção
        HttpClientErrorException exception = new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Erro ao buscar assistidos");
        Mockito.when(assistidoService.buscarAssistidosPorNome(nome, pageable)).thenThrow(exception);

        // Verifica se o método lança a exceção esperada
        HttpClientErrorException thrown = assertThrows(HttpClientErrorException.class, () -> {
            listaAssistidosPorNomeController.buscarAssistidosPorNome(nome, pageable);
        });

        // Verifica o status e a mensagem da exceção
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
        assertEquals("400 400 Erro ao buscar assistidos", thrown.getMessage());
    }
}