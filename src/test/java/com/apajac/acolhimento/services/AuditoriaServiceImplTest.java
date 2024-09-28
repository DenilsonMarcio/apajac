package com.apajac.acolhimento.services;

import com.apajac.acolhimento.domain.entities.AuditoriaEntity;
import com.apajac.acolhimento.exceptions.BusinessException;
import com.apajac.acolhimento.repositories.AuditoriaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class AuditoriaServiceImplTest {

    @Mock
    private AuditoriaRepository repository;

    @InjectMocks
    private AuditoriaServiceImpl auditoriaService;

    @Test
    void inserirDadosDeAuditoria_DeveSalvarAuditoriaComSucesso() {
        // Dados de entrada
        Long idUsuario = 1L;
        String tipo = "UPDATE";
        String servico = "UsuarioService";
        String body = "Dados alterados";

        // Configuração do comportamento esperado do repositório
        Mockito.when(repository.save(any(AuditoriaEntity.class))).thenAnswer(invocation -> {
            // Simula o comportamento de salvar com sucesso
            return invocation.getArgument(0); // Retorna o argumento passado para a chamada do método save
        });

        // Chama o método a ser testado
        auditoriaService.inserirDadosDeAuditoria(idUsuario, tipo, servico, body);

        // Verificações
        Mockito.verify(repository).save(any(AuditoriaEntity.class));
    }

    @Test
    void inserirDadosDeAuditoria_DeveLancarBusinessExceptionQuandoErroAoSalvar() {
        // Dados de entrada
        Long idUsuario = 1L;
        String tipo = "UPDATE";
        String servico = "UsuarioService";
        String body = "Dados alterados";

        // Configuração do comportamento esperado do repositório
        Mockito.when(repository.save(any(AuditoriaEntity.class))).thenThrow(new RuntimeException("Erro ao salvar"));

        // Verifica se o método lança a exceção esperada
        BusinessException exception = assertThrows(BusinessException.class, () ->
                auditoriaService.inserirDadosDeAuditoria(idUsuario, tipo, servico, body));

        // Verifica se a exceção contém a mensagem correta
        assertEquals("Não foi possivel inserir dados de auditoria.", exception.getMessage());
    }

    @Test
    void listarHistoricoDeAlteracoes_DeveRetornarHistorico() {
        // Dados de entrada
        Pageable pageable = Pageable.unpaged();
        AuditoriaEntity auditoriaEntity = new AuditoriaEntity();
        auditoriaEntity.setIdUsuario(1L);
        auditoriaEntity.setTipo("UPDATE");
        auditoriaEntity.setServico("UsuarioService");
        auditoriaEntity.setBody("Dados alterados");
        auditoriaEntity.setData(LocalDateTime.now());

        Page<AuditoriaEntity> auditoriaPage = new PageImpl<>(Collections.singletonList(auditoriaEntity), pageable, 1);

        // Configuração do comportamento esperado do repositório
        Mockito.when(repository.findAll(pageable)).thenReturn(auditoriaPage);

        // Chama o método a ser testado
        Page<AuditoriaEntity> result = auditoriaService.listarHistoricoDeAlteracoes(pageable);

        // Verificações
        assertEquals(1, result.getTotalElements());
        assertEquals(auditoriaEntity, result.getContent().get(0));
    }
}