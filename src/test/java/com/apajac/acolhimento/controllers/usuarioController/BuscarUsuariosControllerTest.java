package com.apajac.acolhimento.controllers.usuarioController;

import com.apajac.acolhimento.controllers.usuario.BuscarUsuariosController;
import com.apajac.acolhimento.domain.dtos.ListaUsuarioDTO;
import com.apajac.acolhimento.domain.dtos.UsuarioSemSenhaDTO;
import com.apajac.acolhimento.domain.entities.UsuarioEntity;
import com.apajac.acolhimento.mappers.UsuarioMapper;
import com.apajac.acolhimento.services.interfaces.UsuarioService;
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

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class BuscarUsuariosControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private UsuarioMapper usuarioMapper;

    @InjectMocks
    private BuscarUsuariosController buscarUsuariosController;

    @Test
    void dadoPaginaValida_quandoListarUsuarios_entaoRetorneListaUsuarioDTO() {
        // Configuração dos parâmetros de entrada
        Pageable pageable = PageRequest.of(0, 10);

        // Configuração dos dados de retorno do serviço
        UsuarioEntity usuarioEntity1 = new UsuarioEntity();
        usuarioEntity1.setId(1L);
        usuarioEntity1.setNome("Usuario 1");

        UsuarioEntity usuarioEntity2 = new UsuarioEntity();
        usuarioEntity2.setId(2L);
        usuarioEntity2.setNome("Usuario 2");

        List<UsuarioEntity> usuarioEntities = Arrays.asList(usuarioEntity1, usuarioEntity2);
        Page<UsuarioEntity> usuarioPage = new PageImpl<>(usuarioEntities, pageable, usuarioEntities.size());

        // Configuração dos dados de retorno do mapeador
        UsuarioSemSenhaDTO usuarioDTO1 = new UsuarioSemSenhaDTO();
        usuarioDTO1.setId(1L);
        usuarioDTO1.setNome("Usuario 1");

        UsuarioSemSenhaDTO usuarioDTO2 = new UsuarioSemSenhaDTO();
        usuarioDTO2.setId(2L);
        usuarioDTO2.setNome("Usuario 2");

        List<UsuarioSemSenhaDTO> usuarioDTOs = Arrays.asList(usuarioDTO1, usuarioDTO2);

        // Configuração do comportamento esperado do serviço e do mapeador
        Mockito.when(usuarioService.listarUsuarios(pageable)).thenReturn(usuarioPage);
        Mockito.when(usuarioMapper.convertEntitesToDtos(usuarioEntities)).thenReturn(usuarioDTOs);

        // Chama o método a ser testado
        ResponseEntity<ListaUsuarioDTO> response = buscarUsuariosController.listarUsuarios(pageable);

        // Assert
        // Verifica se o status da resposta é OK e se o corpo é o esperado
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(usuarioDTOs, response.getBody().getUsuarios());
        assertEquals(usuarioPage.isLast(), response.getBody().getIsLastPage());
    }

    @Test
    void dadoExcecao_quandoListarUsuarios_entaoLancaHttpClientErrorException() {
        // Configuração dos parâmetros de entrada
        Pageable pageable = PageRequest.of(0, 10);

        // Configuração do comportamento esperado do serviço para lançar uma exceção
        HttpClientErrorException exception = new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Erro ao listar usuários");
        Mockito.when(usuarioService.listarUsuarios(pageable)).thenThrow(exception);

        // Verifica se o método lança a exceção esperada
        HttpClientErrorException thrown = assertThrows(HttpClientErrorException.class, () -> {
            buscarUsuariosController.listarUsuarios(pageable);
        });

        // Verifica o status e a mensagem da exceção
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
        assertEquals("400 400 Erro ao listar usuários", thrown.getMessage());
    }
}
