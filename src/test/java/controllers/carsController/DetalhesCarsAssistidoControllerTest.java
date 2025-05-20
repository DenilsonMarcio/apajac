package controllers.carsController;

import com.apajac.acolhimento.controllers.carsController.DetalhesCarsAssistidoController;
import com.apajac.acolhimento.domain.dtos.DetalhesCarsAssistidoDTO;
import com.apajac.acolhimento.domain.dtos.RespostaCarsDTO;
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
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DetalhesCarsAssistidoControllerTest {

    @InjectMocks
    private DetalhesCarsAssistidoController detalhesCarsAssistidoController;

    @Mock
    private ListarCarsService listarCarsService;

    @Test
    void listarDetalhesCarsPorAssistido_deveRetornarDetalhesCarsQuandoAssistidoExistir() {

        // inicializando as entidades
        RespostaCarsDTO respostaCarsDTO = new RespostaCarsDTO();
        respostaCarsDTO.setPergunta(1);
        respostaCarsDTO.setResposta(5.0);

        DetalhesCarsAssistidoDTO detalhesCarsAssistidoDTO = new DetalhesCarsAssistidoDTO();
        detalhesCarsAssistidoDTO.setData(LocalDate.now());
        detalhesCarsAssistidoDTO.setPontuacao(20.0);
        List<RespostaCarsDTO> detalhes  = Collections.singletonList(respostaCarsDTO);
        detalhesCarsAssistidoDTO.setDetalhes(detalhes);

        // mocando a funcão
        when(listarCarsService.detalhesCarsPorAssistidoEData(1L)).thenReturn(detalhesCarsAssistidoDTO);

        //chamando a funcao
        ResponseEntity<DetalhesCarsAssistidoDTO> detalhesCarsAssistido = detalhesCarsAssistidoController.listarDetalhesCarsPorAssistido(1L);

        // Testando se retorna o código correto
        Assertions.assertEquals(HttpStatus.OK, detalhesCarsAssistido.getStatusCode(), "O código de status não está correto.");

        // Testando se retorna a quantidade correta de RespostaCarsDTO
        Assertions.assertEquals(1, detalhesCarsAssistido.getBody().getDetalhes().size(), "A quantidade de detalhes não está correta.");

        // Testando se retorna a entidade correta de RespostaCarsDTO
        Assertions.assertEquals(20.0, detalhesCarsAssistido.getBody().getPontuacao(), "A pontuação não está correta.");

        // Testando se retorna os valores corretos das RespostaCarsDTO
        RespostaCarsDTO respostaRetornada = detalhesCarsAssistido.getBody().getDetalhes().get(0);
        Assertions.assertEquals(1, respostaRetornada.getPergunta(), "A pergunta não está correta.");

    }

    @Test
    void listarDetalhesCarsPorAssistido_DeveLancarExceptionQuandoNaoEncontrarDetalhes() {

        // Inicializando a exception com a mensagem
        HttpClientErrorException Httpexception = new HttpClientErrorException(HttpStatus.NOT_FOUND, "Lista de detalhes de cars não encontrada");

        // mocando a funcão
        when(listarCarsService.detalhesCarsPorAssistidoEData(1L)).thenThrow(Httpexception);

        //chamando a funcao
        HttpClientErrorException notFoundException = assertThrows(HttpClientErrorException.class, () -> {
            listarCarsService.detalhesCarsPorAssistidoEData(1L);
        });

        // Testando se retorna o código correto da exceção
        Assertions.assertEquals(HttpStatus.NOT_FOUND, notFoundException.getStatusCode(), "O código de status da exceção não está correto.");

        // Testando se retorna a mensagem correta da exceção
        Assertions.assertEquals("404 Lista de detalhes de cars não encontrada", notFoundException.getMessage(), "A mensagem da exceção não está correta.");

    }


}
