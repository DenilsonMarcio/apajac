package controllers.relatorioController;

import com.apajac.acolhimento.controllers.relatorioController.AssistidosComSemPaiController;
import com.apajac.acolhimento.services.interfaces.AssistidosComSemPaiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AssistidosComSemPaiControllerTest {

    private AssistidosComSemPaiService assistidosComSemPaiService;
    private AssistidosComSemPaiController controller;

    @BeforeEach
    void setUp() {
        assistidosComSemPaiService = mock(AssistidosComSemPaiService.class);
        controller = new AssistidosComSemPaiController(assistidosComSemPaiService);
    }

    @Test
    void testGetAssistidosComSemPai_sucesso() {
        Map<String, List<String>> mockResponse = Map.of(
                "Labels", List.of("Com Pai", "Sem Pai"),
                "Values", List.of("10", "5")
        );
        when(assistidosComSemPaiService.totalAssistidosComSemPai()).thenReturn(mockResponse);

        ResponseEntity<Map<String, List<String>>> response = controller.getAssistidosComSemPai();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockResponse, response.getBody());
        verify(assistidosComSemPaiService, times(1)).totalAssistidosComSemPai();
    }

    @Test
    void testGetAssistidosComSemPai_erroHttpClient() {
        when(assistidosComSemPaiService.totalAssistidosComSemPai())
                .thenThrow(new HttpClientErrorException(org.springframework.http.HttpStatus.BAD_REQUEST, "Erro"));

        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class,
                () -> controller.getAssistidosComSemPai());

        assertEquals(400, exception.getStatusCode().value());
        verify(assistidosComSemPaiService, times(1)).totalAssistidosComSemPai();
    }
}
