package controllers.relatorioController;

import com.apajac.acolhimento.controllers.relatorioController.RendaController;
import com.apajac.acolhimento.services.interfaces.FamiliarMediaRendaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RendaControllerTest {

    private FamiliarMediaRendaService familiarMediaRendaService;
    private RendaController rendaController;

    @BeforeEach
    void setUp() {
        familiarMediaRendaService = mock(FamiliarMediaRendaService.class);
        rendaController = new RendaController(familiarMediaRendaService);
    }

    @Test
    void testCalcularMediaGeralRenda_sucesso() {
        Map<String, List<String>> mockResponse = Map.of(
                "Labels", List.of("Média Geral"),
                "Values", List.of("1200")
        );
        when(familiarMediaRendaService.calcularMediaGeralRenda()).thenReturn(mockResponse);

        ResponseEntity<Map<String, List<String>>> response = rendaController.calcularMediaGeralRenda();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockResponse, response.getBody());
        verify(familiarMediaRendaService, times(1)).calcularMediaGeralRenda();
    }

    @Test
    void testCalcularMediaGeralRenda_erroHttpClient() {
        when(familiarMediaRendaService.calcularMediaGeralRenda())
                .thenThrow(new HttpClientErrorException(org.springframework.http.HttpStatus.BAD_REQUEST, "Erro"));

        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class,
                () -> rendaController.calcularMediaGeralRenda());

        assertEquals(400, exception.getStatusCode().value());
        verify(familiarMediaRendaService, times(1)).calcularMediaGeralRenda();
    }

    @Test
    void testCalcularMediaRendaPorAssistido_sucesso() {
        Map<String, List<String>> mockResponse = Map.of(
                "Labels", List.of("Assistido A", "Assistido B"),
                "Values", List.of("1000", "1500")
        );
        when(familiarMediaRendaService.calcularMediaRendaPorAssistido()).thenReturn(mockResponse);

        ResponseEntity<Map<String, List<String>>> response = rendaController.calcularMediaRendaPorAssistido();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockResponse, response.getBody());
        verify(familiarMediaRendaService, times(1)).calcularMediaRendaPorAssistido();
    }

    @Test
    void testCalcularMediaRendaPorAssistido_erroHttpClient() {
        when(familiarMediaRendaService.calcularMediaRendaPorAssistido())
                .thenThrow(new HttpClientErrorException(org.springframework.http.HttpStatus.BAD_REQUEST, "Erro"));

        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class,
                () -> rendaController.calcularMediaRendaPorAssistido());

        assertEquals(400, exception.getStatusCode().value());
        verify(familiarMediaRendaService, times(1)).calcularMediaRendaPorAssistido();
    }

    @Test
    void testCalcularFaixasDeRenda_sucesso() {
        Map<String, List<String>> mockResponse = Map.of(
                "Labels", List.of("0-1000", "1001-2000", "2001-3000"),
                "Values", List.of("10", "20", "5")
        );
        when(familiarMediaRendaService.calcularFaixasDeRenda()).thenReturn(mockResponse);

        ResponseEntity<Map<String, List<String>>> response = rendaController.calcularFaixasDeRenda();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockResponse, response.getBody());
        verify(familiarMediaRendaService, times(1)).calcularFaixasDeRenda();
    }

    @Test
    void testCalcularFaixasDeRenda_erroHttpClient() {
        when(familiarMediaRendaService.calcularFaixasDeRenda())
                .thenThrow(new HttpClientErrorException(org.springframework.http.HttpStatus.BAD_REQUEST, "Erro"));

        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class,
                () -> rendaController.calcularFaixasDeRenda());

        assertEquals(400, exception.getStatusCode().value());
        verify(familiarMediaRendaService, times(1)).calcularFaixasDeRenda();
    }
}
