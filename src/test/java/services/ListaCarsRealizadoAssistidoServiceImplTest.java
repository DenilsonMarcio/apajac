package services;

import com.apajac.acolhimento.domain.dtos.DetalhesCarsAssistidoDTO;
import com.apajac.acolhimento.domain.dtos.NomeAssistidoCarsDTO;
import com.apajac.acolhimento.domain.entities.AssistidoEntity;
import com.apajac.acolhimento.domain.entities.CarsEntity;
import com.apajac.acolhimento.domain.entities.RespostaCarsEntity;
import com.apajac.acolhimento.exceptions.NotFoundException;
import com.apajac.acolhimento.repositories.AssistidoRepository;
import com.apajac.acolhimento.repositories.CarsRepository;
import com.apajac.acolhimento.services.ListaCarsRealizadoAssistidoServiceImpl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ListaCarsRealizadoAssistidoServiceImplTest {


    @InjectMocks
    private ListaCarsRealizadoAssistidoServiceImpl listaCarsRealizadoAssistidoServiceImpl;

    @Mock
    private CarsRepository carsRepository;

    @Mock
    private AssistidoRepository assistidoRepository;

    @Test
    void listarCarsPorAssistido_DeveRetornarCarsQuandoExistir() {

        // inicializando as entidades
        AssistidoEntity assistidoEntity = new AssistidoEntity();
        assistidoEntity.setId(1L);
        assistidoEntity.setNome("Leo");

        CarsEntity carsEntity = new CarsEntity();
        carsEntity.setId(1L);
        carsEntity.setPontuacao(40.0);
        carsEntity.setAssistido(assistidoEntity);

        //inicializando o segundo cars para testar se ele consegue buscar TODOS os cars anexados ao mesmo assistido
        CarsEntity carsEntity2 = new CarsEntity();
        carsEntity2.setId(2L);
        carsEntity2.setPontuacao(38.0);
        carsEntity2.setAssistido(assistidoEntity);

        // mocando as funcoes
        when(assistidoRepository.findById(assistidoEntity.getId())).thenReturn(Optional.of(assistidoEntity));
        when(carsRepository.findByAssistidoId(assistidoEntity.getId())).thenReturn(Arrays.asList(carsEntity, carsEntity2));

        //chamando a funcao
        NomeAssistidoCarsDTO nomeAssistidoCarsDTO = listaCarsRealizadoAssistidoServiceImpl.listarCarsPorAssistido(assistidoEntity.getId());

        // Testando se a lista contém os 2 elementos mockados
        Assertions.assertEquals(2, nomeAssistidoCarsDTO.getCars().size(), "A lista de cars não contém o número esperado de elementos.");

        // Testando se o id do primeiro elemento da lista é o id do primeiro item mockado
        Assertions.assertEquals(1, nomeAssistidoCarsDTO.getCars().get(0).getId(), "O ID do primeiro cars retornado não está correto.");
    }

    @Test
    void llistarCarsPorAssistido_DeveLancarNotFoundExceptionQuandoAssistidoNaoEncontrado() {

        // mocando a funcão
        when(assistidoRepository.findById(1L)).thenReturn(Optional.empty());

        //chamando a funcao
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            listaCarsRealizadoAssistidoServiceImpl.listarCarsPorAssistido(1L);
        });

        //testando se setorna a NotFoundException se o assistidoRepository retornar vazio
        Assertions.assertEquals("Assistido não encontrado!", exception.getMessage(), "A mensagem da exceção não está correta.");
    }

    @Test
    void detalhesCarsPorAssistidoEData_DeveRetornarListaDeRespostaQuandoExistir () {

        //inicializando duas respostas para anexar a um cars
        RespostaCarsEntity respostaCarsEntity = new RespostaCarsEntity();
        respostaCarsEntity.setId(1L);
        respostaCarsEntity.setPergunta(1);
        respostaCarsEntity.setResposta(3.0);

        RespostaCarsEntity respostaCarsEntity2 = new RespostaCarsEntity();
        respostaCarsEntity.setId(2L);
        respostaCarsEntity.setPergunta(2);
        respostaCarsEntity.setResposta(6.0);

        // inicializando as entidades
        AssistidoEntity assistidoEntity = new AssistidoEntity();
        assistidoEntity.setId(1L);
        assistidoEntity.setNome("Leo");

        CarsEntity carsEntity = new CarsEntity();
        carsEntity.setId(1L);
        carsEntity.setPontuacao(40.0);
        carsEntity.setAssistido(assistidoEntity);
        carsEntity.setRespostas(Arrays.asList(respostaCarsEntity, respostaCarsEntity2));

        // mocando as funcoes
        when(carsRepository.findById(1L)).thenReturn(Optional.of(carsEntity));

        //chamando a funcao
        DetalhesCarsAssistidoDTO detalhes = listaCarsRealizadoAssistidoServiceImpl.detalhesCarsPorAssistidoEData(carsEntity.getId());

        //testando se retorna as duas respostas do carsEntity
        Assertions.assertEquals(2, detalhes.getDetalhes().size(), "A quantidade de respostas retornadas está incorreta.");
        Assertions.assertEquals(6.0, detalhes.getDetalhes().get(0).getResposta(), "A resposta da pergunta 1 não está correta.");

    }

    @Test
    void detalhesCarsPorAssistidoEData_DeveLancarNotFoundExceptionQuandoNaoEncontrado() {

        // mocando as funcoes
        when(carsRepository.findById(1L)).thenReturn(Optional.empty());

        //chamando a funcao
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            listaCarsRealizadoAssistidoServiceImpl.detalhesCarsPorAssistidoEData(1L);
        });

        //testando se torna a NotFoundException se o carsRepository retornar vazio
        Assertions.assertEquals("Cars não encontrado!", exception.getMessage(), "A mensagem da exceção não está correta.");
    }
}
