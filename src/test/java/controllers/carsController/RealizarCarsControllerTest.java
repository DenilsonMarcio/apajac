package controllers.carsController;

import com.apajac.acolhimento.controllers.carsController.RealizarCarsController;
import com.apajac.acolhimento.domain.dtos.CarsDTO;
import com.apajac.acolhimento.domain.dtos.RespostaCarsDTO;
import com.apajac.acolhimento.services.interfaces.RealizarCarsAssistidoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

//Configs para teste do Weblayer
@ExtendWith(SpringExtension.class)
public class RealizarCarsControllerTest {

    @Mock
    private RealizarCarsAssistidoService realizarCarsAssistidoService;

    @InjectMocks
    private RealizarCarsController realizarCarsController;

    //Teste do cadastro

    @Test
    void realizarCarsAssistidoTest_DeveCadastrarCars() {
        // Criação de um modelo de entrada (DTO) sem ID (novo cadastro)
        CarsDTO carsDTO = createCarsDTO();

        // Configuração do comportamento esperado do serviço
        Mockito.doNothing().when(realizarCarsAssistidoService).realizarCarsAssistido(carsDTO);

        // Chama o método a ser testado
        ResponseEntity<String> response = realizarCarsController.realizarCarsAssistido(carsDTO);

        // Verifica se o status da resposta e o corpo são os esperados
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Cars realizado com sucesso.", response.getBody());
    }

    //Teste de ID Nulo

    @Test
    void RealizarCarsAssistido_DeveLancarExceptionQuandoIDNulo() {
        // Criação de um modelo de entrada (DTO)
        CarsDTO carsDTO = createCarsDTO();

        // Configuração do comportamento esperado do serviço para lançar uma exceção
        HttpClientErrorException exception = new HttpClientErrorException(HttpStatus.NOT_ACCEPTABLE, "Cars precisa ser associado a um assistido");
        Mockito.doThrow(exception).when(realizarCarsAssistidoService).realizarCarsAssistido(carsDTO);

        // Verifica se o método lança a exceção esperada
        HttpClientErrorException thrown = assertThrows(HttpClientErrorException.class, () ->
                realizarCarsController.realizarCarsAssistido(carsDTO));

        // Verifica o status e a mensagem da exceção
        assertEquals(HttpStatus.NOT_ACCEPTABLE, thrown.getStatusCode());
        assertEquals("406 406 Cars precisa ser associado a um assistido", thrown.getMessage());
    }

    //Teste de 404

    @Test
    void RealizarCarsAssistido_DeveLancarExceptionQuandoNaoEncontrarAssistido() {
        // Criação de um modelo de entrada (DTO)
        CarsDTO carsDTO = createCarsDTO();

        // Configuração do comportamento esperado do serviço para lançar uma exceção
        HttpClientErrorException exception = new HttpClientErrorException(HttpStatus.NOT_FOUND, "Assistido não encontrado!");
        Mockito.doThrow(exception).when(realizarCarsAssistidoService).realizarCarsAssistido(carsDTO);

        // Verifica se o método lança a exceção esperada
        HttpClientErrorException thrown = assertThrows(HttpClientErrorException.class, () ->
                realizarCarsController.realizarCarsAssistido(carsDTO));

        // Verifica o status e a mensagem da exceção
        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals("404 404 Assistido não encontrado!", thrown.getMessage());}

    //Gerador dinâmico de DTO

    private CarsDTO createCarsDTO() {
        CarsDTO carsDTO = new CarsDTO();
        List<RespostaCarsDTO> respostaCarsDTOS = new ArrayList<>();
        carsDTO.setId(1L);
        carsDTO.setPontuacao(0D);
        for (int i = 1; i <= 15; i++) {
            Random random = new Random();
            RespostaCarsDTO respostaCarsDTO = new RespostaCarsDTO();
            respostaCarsDTO.setPergunta(i);
            respostaCarsDTO.setResposta((Arrays.asList(0, 1.5, 2.5, 3.5).get(random.nextInt(4))).doubleValue());
            respostaCarsDTO.setObservacoes(Arrays.asList("(  0.0)", "( 0.0 )", "(0.0  )").get(random.nextInt(3)));
            carsDTO.setPontuacao(carsDTO.getPontuacao() + respostaCarsDTO.getResposta());
            respostaCarsDTOS.add(respostaCarsDTO);
        }
        carsDTO.setCars(respostaCarsDTOS);
        return carsDTO;
    }
}