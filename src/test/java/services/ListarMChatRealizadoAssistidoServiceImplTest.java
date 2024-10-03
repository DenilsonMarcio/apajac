package services;

import com.apajac.acolhimento.domain.dtos.DetalhesMChatAssistidoDTO;
import com.apajac.acolhimento.domain.dtos.NomeAssistidoMChatDTO;
import com.apajac.acolhimento.domain.entities.AssistidoEntity;
import com.apajac.acolhimento.domain.entities.MChatEntity;
import com.apajac.acolhimento.domain.entities.RespostaMChatEntity;
import com.apajac.acolhimento.exceptions.NotFoundException;
import com.apajac.acolhimento.repositories.MChatRepository;
import com.apajac.acolhimento.services.ListarMChatRealizadoAssistidoServiceImpl;
import com.apajac.acolhimento.utils.AssistidoUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ListarMChatRealizadoAssistidoServiceImplTest {

   @InjectMocks
   ListarMChatRealizadoAssistidoServiceImpl listarMChatRealizadoAssistidoService;

   @Mock
   MChatRepository mChatRepository;

    @Mock
    private AssistidoUtils assistidoUtils;

   @Test
    void listarMChatsPorAssistido_DeveRetornarMChatsQuandoExistirem() {

      AssistidoEntity assistidoEntity = new AssistidoEntity();
      assistidoEntity.setId(1L);
      assistidoEntity.setNome("Leo");

      MChatEntity mChatEntity = new MChatEntity();
      mChatEntity.setId(1L);
      mChatEntity.setAssistido(assistidoEntity);
      MChatEntity mChatEntity2 = new MChatEntity();
      mChatEntity2.setId(2L);
      mChatEntity2.setAssistido(assistidoEntity);

       when(assistidoUtils.verificaAssistido(assistidoEntity.getId())).thenReturn(assistidoEntity);
       when(mChatRepository.findByAssistidoId(assistidoEntity.getId())).thenReturn(Arrays.asList(mChatEntity, mChatEntity2));

      NomeAssistidoMChatDTO resultado = listarMChatRealizadoAssistidoService.listarMChatPorAssistido(assistidoEntity.getId());

      assertEquals(2, resultado.getMchat().size(), "Número de MChats divergente");
      assertEquals("Leo", resultado.getNomeAssistido(), "Nome do assistido divergente");
      assertEquals(1, resultado.getMchat().get(0).getId(),"Id o Mchat divergente" );

   }

   @Test
   void detalhesMChatPorAssistidoEData_DeveRetornarDetalhesCorretosQuandoExistirem() {

      AssistidoEntity assistidoEntity = new AssistidoEntity();
      assistidoEntity.setId(1L);
      assistidoEntity.setNome("Leo");

      RespostaMChatEntity resposta1 = new RespostaMChatEntity();
      resposta1.setPergunta(1);
      resposta1.setResposta(true);
      RespostaMChatEntity resposta2 = new RespostaMChatEntity();
      resposta2.setPergunta(2);
      resposta2.setResposta(false);

      MChatEntity mChatEntity = new MChatEntity();
      mChatEntity.setId(1L);
      mChatEntity.setAssistido(assistidoEntity);
      mChatEntity.setRespostas(Arrays.asList(resposta1, resposta2));

      when(assistidoUtils.verificaAssistido(assistidoEntity.getId())).thenReturn(assistidoEntity);
      when(mChatRepository.findById(1L)).thenReturn(Optional.of(mChatEntity));


      DetalhesMChatAssistidoDTO resultado = listarMChatRealizadoAssistidoService.detalhesMChatPorAssistidoEData(assistidoEntity.getId());

      assertEquals("Leo", resultado.getNomeAssistido(), "Nome do assistido divergente");
      assertEquals(2, resultado.getDetalhes().size(), "Número de respostas do MChat divergente");
      assertEquals(2, resultado.getDetalhes().get(1).getPergunta() ,"Pergunta do mChat divergente" );
      assertEquals(true,resultado.getDetalhes().get(0).getResposta(), "Resposta do mChat divergente" );


   }

   @Test
   void detalhesMChatPorAssistidoEData_DeveLancarNotFoundExceptionQuandoNaoEncontrado() {

      when(mChatRepository.findById(1L)).thenThrow(new NotFoundException("M-Chat não encontrado!"));

      NotFoundException exception = assertThrows(NotFoundException.class, () -> {
         listarMChatRealizadoAssistidoService.detalhesMChatPorAssistidoEData(1L);
      });

      assertEquals("M-Chat não encontrado!", exception.getMessage(), "Exception divergente");
   }

}
