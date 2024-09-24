package controllers.carsController;

import com.apajac.acolhimento.controllers.carsController.ListaCarsRealizadoAssistidoController;
import com.apajac.acolhimento.domain.dtos.CarsDataPontuacaoDTO;
import com.apajac.acolhimento.domain.dtos.NomeAssistidoCarsDTO;
import com.apajac.acolhimento.services.interfaces.ListarCarsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ListaCarsRealizadoAssistidoControllerTest {

    @InjectMocks
    private ListaCarsRealizadoAssistidoController listaCarsRealizadoAssistidoController;

    @Mock
    private ListarCarsService listarCarsService;

    @Test
    void listarCarsPorAssistido_DeveRetornarListaDeCarsPorAssistido() {

        // inicializando as entidades
        CarsDataPontuacaoDTO carsDataPontuacaoDTO = new CarsDataPontuacaoDTO();
        carsDataPontuacaoDTO.setId(1L);
        carsDataPontuacaoDTO.setData(LocalDate.now());
        carsDataPontuacaoDTO.setPontuacao(40.0);

        // inicializando as entidades
        CarsDataPontuacaoDTO carsDataPontuacaoDTO2 = new CarsDataPontuacaoDTO();
        carsDataPontuacaoDTO2.setId(2L);
        carsDataPontuacaoDTO2.setData(LocalDate.now());
        carsDataPontuacaoDTO2.setPontuacao(60.0);

        NomeAssistidoCarsDTO nomeAssistidoCarsDTO = new NomeAssistidoCarsDTO();
        nomeAssistidoCarsDTO.setNomeAssistido("Test 1");
        List<CarsDataPontuacaoDTO> cars = Arrays.asList(carsDataPontuacaoDTO, carsDataPontuacaoDTO2);
        nomeAssistidoCarsDTO.setCars(cars);

        // mocando a funcão
        when(listarCarsService.listarCarsPorAssistido(1L)).thenReturn(nomeAssistidoCarsDTO);

        //chamando a funcao
        ResponseEntity<NomeAssistidoCarsDTO> ListaCarsRealizadoAssistidoController = listaCarsRealizadoAssistidoController
                .listarCarsPorAssistido(1L);

        // Testando se retorna o código correto
        Assertions.assertEquals(HttpStatus.OK, ListaCarsRealizadoAssistidoController.getStatusCode(), "O código de status retornado não está correto.");

        // Testando se retorna a entidade correta
        Assertions.assertEquals("Test 1", ListaCarsRealizadoAssistidoController.getBody().getNomeAssistido(), "O nome do assistido retornado não está correto.");
        Assertions.assertEquals(2, ListaCarsRealizadoAssistidoController.getBody().getCars().size(), "A quantidade de cars retornada não está correta.");
        Assertions.assertEquals(60.0, ListaCarsRealizadoAssistidoController.getBody().getCars().get(1).getPontuacao(), "A pontuação do Cars 2 retornada não está correta.");
    }

    @Test
    void listarCarsPorAssistido_DeveRetornarException() {

        // inicializando a exception com a mensagem, pois o retorno normal vem dentro deo service e nao dentro do controller
        HttpClientErrorException Httpexception = new HttpClientErrorException(HttpStatus.NOT_FOUND, "Lista de cars não encontrada");

        // mocando a funcão
        when(listarCarsService.listarCarsPorAssistido(1L)).thenThrow(Httpexception);

        //chamando a funcao
        HttpClientErrorException notFoundException = assertThrows(HttpClientErrorException.class, () -> {
            listarCarsService.listarCarsPorAssistido(1L);
        });

        // Testando se retorna o código correto da exceção
        Assertions.assertEquals(HttpStatus.NOT_FOUND, notFoundException.getStatusCode(), "O código de status retornado não está correto.");

        // Testando se retorna a mensagem correta da exceção
        Assertions.assertEquals("Lista de cars não encontrada", notFoundException.getMessage(), "A mensagem da exceção retornada não está correta.");
    }

}
