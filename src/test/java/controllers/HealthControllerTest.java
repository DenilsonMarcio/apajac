package controllers;

import com.apajac.acolhimento.controllers.HealthController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class HealthControllerTest {

    @InjectMocks
    private HealthController healthController;

    @Test
    void quandoInicializarServidor_entaoRetorneMensagemDeSucesso() {
        // Chama o método a ser testado
        ResponseEntity<String> response = healthController.inicializarServidor();

        // Configura o formato da data esperada
        ZoneId zoneId = ZoneId.systemDefault();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy - HH:mm:ss");
        LocalDateTime now = LocalDateTime.now(ZonedDateTime.now().getOffset());
        String expectedMessage = "Aplicação Apajac rodando em " + zoneId +
                "\n Data da solicitação " + now.format(formatter) +
                "\n Respondendo com sucesso!!!]";

        // Assert
        // Verifica se o status da resposta e o corpo são os esperados
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().contains("Aplicação Apajac rodando em " + zoneId));
        assertTrue(response.getBody().contains("Respondendo com sucesso!!!"));
    }

    @Test
    void quandoInicializarServidorComErro_entaoLancaRuntimeException() {
        // Simula uma exceção ao obter a hora atual
        HealthController healthControllerSpy = Mockito.spy(healthController);
        Mockito.doThrow(new RuntimeException("Erro simulado")).when(healthControllerSpy).inicializarServidor();

        // Verifica se o método lança a exceção esperada
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            healthControllerSpy.inicializarServidor();
        });

        // Verifica a mensagem da exceção
        assertEquals("Erro simulado", thrown.getMessage());
    }
}
