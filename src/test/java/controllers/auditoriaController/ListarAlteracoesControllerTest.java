package controllers.auditoriaController;

import com.apajac.acolhimento.controllers.auditoriaController.ListarAlteracoesController;
import com.apajac.acolhimento.domain.entities.AuditoriaEntity;
import com.apajac.acolhimento.services.interfaces.AuditoriaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class ListarAlteracoesControllerTest {

    @Mock
    private AuditoriaService auditoriaService;

    @InjectMocks
    private ListarAlteracoesController listarAlteracoesController;

    @Test
    void dadoListarHistoricoDeAlteracoes_entaoRetornePaginaDeAuditoriaEntity() {
        // Configuração dos parâmetros de entrada
        Pageable pageable = PageRequest.of(0, 10);

        // Criação de um modelo de entidade de auditoria
        AuditoriaEntity auditoriaEntity = new AuditoriaEntity();
        auditoriaEntity.setId(1L);

        Page<AuditoriaEntity> auditoriaEntitiesPage = new PageImpl<>(Collections.singletonList(auditoriaEntity), pageable, 1);

        // Configuração do comportamento esperado do serviço
        Mockito.when(auditoriaService.listarHistoricoDeAlteracoes(pageable)).thenReturn(auditoriaEntitiesPage);

        // Chama o método a ser testado
        ResponseEntity<Page<AuditoriaEntity>> response = listarAlteracoesController.listarHistoricoDeAlteracoes(pageable);

        // Assert
        // Verifica se o status da resposta e o corpo são os esperados
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(auditoriaEntitiesPage, response.getBody());
    }

    @Test
    void dadoListarHistoricoDeAlteracoesComErro_entaoRetorneHttpClientErrorException() {
        // Configuração dos parâmetros de entrada
        Pageable pageable = PageRequest.of(0, 10);

        // Configuração do comportamento esperado do serviço para lançar uma exceção
        HttpClientErrorException exception = new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Erro ao listar histórico de alterações");
        Mockito.when(auditoriaService.listarHistoricoDeAlteracoes(pageable)).thenThrow(exception);

        // Verifica se o método lança a exceção esperada
        HttpClientErrorException thrown = assertThrows(HttpClientErrorException.class, () -> {
            listarAlteracoesController.listarHistoricoDeAlteracoes(pageable);
        });

        // Verifica o status e a mensagem da exceção
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
        assertEquals("400 400 Erro ao listar histórico de alterações", thrown.getMessage());
    }
}
