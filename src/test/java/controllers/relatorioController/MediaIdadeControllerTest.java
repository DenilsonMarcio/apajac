package controllers.relatorioController;

import com.apajac.acolhimento.controllers.relatorioController.MediaIdadeController;
import com.apajac.acolhimento.services.interfaces.AssistidoMediaIdadeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MediaIdadeControllerTest {

    private AssistidoMediaIdadeService assistidoMediaIdadeService;
    private MediaIdadeController mediaIdadeController;

    @BeforeEach
    void setUp() {
        assistidoMediaIdadeService = mock(AssistidoMediaIdadeService.class);
        mediaIdadeController = new MediaIdadeController(assistidoMediaIdadeService);
    }

    // MEDIA GERAL
    @Test
    void testGetMediaIdade_sucesso() {
        Map<String, List<String>> mockResponse = Map.of(
                "Labels", List.of("Média Geral"),
                "Values", List.of("25")
        );
        when(assistidoMediaIdadeService.calcularMediaGeralIdade()).thenReturn(mockResponse);

        ResponseEntity<Map<String, List<String>>> response = mediaIdadeController.getMediaIdade();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockResponse, response.getBody());
        verify(assistidoMediaIdadeService, times(1)).calcularMediaGeralIdade();
    }

    @Test
    void testGetMediaIdade_erroHttpClient() {

        when(assistidoMediaIdadeService.calcularMediaGeralIdade())
                .thenThrow(new HttpClientErrorException(org.springframework.http.HttpStatus.BAD_REQUEST, "Erro"));

        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class,
                () -> mediaIdadeController.getMediaIdade());

        assertEquals(400, exception.getStatusCode().value());
        verify(assistidoMediaIdadeService, times(1)).calcularMediaGeralIdade();
    }

    //FAIXA ETARIA
    @Test
    void testGetMediaPorFaixaEtaria_sucesso() {
        Map<String, List<String>> mockResponse = Map.of(
                "Labels", List.of("0-10", "11-20", "21-30"),
                "Values", List.of("5", "10", "15")
        );
        when(assistidoMediaIdadeService.calcularMediaPorFaixaEtaria()).thenReturn(mockResponse);

        ResponseEntity<Map<String, List<String>>> response = mediaIdadeController.getMediaPorFaixaEtaria();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockResponse, response.getBody());
        verify(assistidoMediaIdadeService, times(1)).calcularMediaPorFaixaEtaria();
    }

    @Test
    void testGetMediaPorFaixaEtaria_erroHttpClient() {
        when(assistidoMediaIdadeService.calcularMediaPorFaixaEtaria())
                .thenThrow(new HttpClientErrorException(org.springframework.http.HttpStatus.BAD_REQUEST, "Erro"));

        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class,
                () -> mediaIdadeController.getMediaPorFaixaEtaria());

        assertEquals(400, exception.getStatusCode().value());
        verify(assistidoMediaIdadeService, times(1)).calcularMediaPorFaixaEtaria();
    }

}
