package com.apajac.acolhimento.services;

import com.apajac.acolhimento.domain.entities.UsuarioEntity;
import com.apajac.acolhimento.domain.enums.AuditoriaEnum;
import com.apajac.acolhimento.exceptions.BusinessException;
import com.apajac.acolhimento.exceptions.NotFoundException;
import com.apajac.acolhimento.repositories.UsuarioRepository;
import com.apajac.acolhimento.services.interfaces.AtualizarStatusUsuarioService;
import com.apajac.acolhimento.services.interfaces.AuditoriaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
class AtualizarStatusUsuarioServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private AuditoriaService auditoria;

    @InjectMocks
    private AtualizarStatusUsuarioServiceImpl atualizarStatusUsuarioService;

    @Test
    void updateStatusUsuario_DeveAtualizarStatusDoUsuario() {
        // Dados de entrada
        Long userId = 1L;
        Long responsavelId = 2L;

        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setId(userId);
        usuarioEntity.setStatus(false);
        usuarioEntity.setRoles(Set.of("Admin"));

        // Configuração do comportamento esperado do repositório
        Mockito.when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuarioEntity));
        Mockito.when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuarioEntity);
        Mockito.doNothing().when(auditoria).inserirDadosDeAuditoria(any(Long.class), any(String.class), any(String.class), any(String.class));

        // Chama o método a ser testado
        atualizarStatusUsuarioService.updateStatusUsuario(userId, responsavelId);

        // Verificações
        assertTrue(usuarioEntity.isStatus());
        Mockito.verify(usuarioRepository).save(usuarioEntity);
        Mockito.verify(auditoria).inserirDadosDeAuditoria(
                responsavelId,
                AuditoriaEnum.UPDATED.getValues(),
                AtualizarStatusUsuarioService.class.getSimpleName(),
                userId.toString()
        );
    }

    @Test
    void updateStatusUsuario_DeveLancarNotFoundExceptionQuandoUsuarioNaoExistir() {
        // Dados de entrada
        Long userId = 1L;
        Long responsavelId = 2L;

        // Configuração do comportamento esperado do repositório
        Mockito.when(usuarioRepository.findById(userId)).thenReturn(Optional.empty());

        // Verifica se o método lança a exceção esperada
        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                atualizarStatusUsuarioService.updateStatusUsuario(userId, responsavelId));

        // Verifica se a exceção contém a mensagem correta
        assertEquals("Usuário não encontrado.", exception.getMessage());
    }

    @Test
    void updateStatusUsuario_DeveLancarBusinessExceptionQuandoUsuarioForRoot() {
        // Dados de entrada
        Long userId = 1L;
        Long responsavelId = 2L;

        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setId(userId);
        usuarioEntity.setRoles(Set.of("root"));

        // Configuração do comportamento esperado do repositório
        Mockito.when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuarioEntity));

        // Verifica se o método lança a exceção esperada
        BusinessException exception = assertThrows(BusinessException.class, () ->
                atualizarStatusUsuarioService.updateStatusUsuario(userId, responsavelId));

        // Verifica se a exceção contém a mensagem correta
        assertEquals("Usuário ROOT não pode ser desativado.", exception.getMessage());
    }

    @Test
    void updateStatusUsuario_DeveLancarBusinessExceptionQuandoTentativaDeDesativarProprioUsuario() {
        // Dados de entrada
        Long userId = 1L;

        // Verifica se o método lança a exceção esperada
        BusinessException exception = assertThrows(BusinessException.class, () ->
                atualizarStatusUsuarioService.updateStatusUsuario(userId, userId));

        // Verifica se a exceção contém a mensagem correta
        assertEquals("Não é possível desativar o seu próprio usuário.", exception.getMessage());
    }

    @Test
    void updateStatusUsuario_DeveChamarAuditoria() {
        // Dados de entrada
        Long userId = 1L;
        Long responsavelId = 2L;

        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setId(userId);
        usuarioEntity.setStatus(false);
        usuarioEntity.setRoles(Set.of("admin"));

        // Configuração do comportamento esperado do repositório
        Mockito.when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuarioEntity));
        Mockito.when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuarioEntity);
        Mockito.doNothing().when(auditoria).inserirDadosDeAuditoria(any(Long.class), any(String.class), any(String.class), any(String.class));

        // Chama o método a ser testado
        atualizarStatusUsuarioService.updateStatusUsuario(userId, responsavelId);

        // Verificações
        Mockito.verify(auditoria).inserirDadosDeAuditoria(
                responsavelId,
                AuditoriaEnum.UPDATED.getValues(),
                AtualizarStatusUsuarioService.class.getSimpleName(),
                userId.toString()
        );
    }
}
