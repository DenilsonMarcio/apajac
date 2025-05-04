package controllers.relatorioController;

import com.apajac.acolhimento.controllers.relatorioController.AssistidosInstituicaoController;
import com.apajac.acolhimento.services.interfaces.AssistidosInstituicaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AssistidosInstituicaoControllerTest {

    private AssistidosInstituicaoService assistidosInstituicaoService;
    private AssistidosInstituicaoController controller;

    @BeforeEach
    void setUp() {
        assistidosInstituicaoService = mock(AssistidosInstituicaoService.class);
        controller = new AssistidosInstituicaoController(assistidosInstituicaoService);
    }

    @Test
    void testGetInstituicoes_sucesso() {
        Map<String, List<String>> mockResponse = Map.of(
                "Labels", List.of("Instituição 1", "Instituição 2"),
                "Values", List.of("10", "5")
        );
        when(assistidosInstituicaoService.totalPorInstituicoes()).thenReturn(mockResponse);

        ResponseEntity<Map<String, List<String>>> response = controller.getInstituicoes();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockResponse, response.getBody());
        verify(assistidosInstituicaoService, times(1)).totalPorInstituicoes();
    }

    @Test
    void testGetInstituicoes_erroHttpClient() {
        when(assistidosInstituicaoService.totalPorInstituicoes())
                .thenThrow(new HttpClientErrorException(org.springframework.http.HttpStatus.BAD_REQUEST, "Erro"));

        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class,
                () -> controller.getInstituicoes());

        assertEquals(400, exception.getStatusCode().value());
        verify(assistidosInstituicaoService, times(1)).totalPorInstituicoes();
    }
}
