package com.apajac.acolhimento.services;

import com.apajac.acolhimento.domain.dtos.LoginDTO;
import com.apajac.acolhimento.domain.dtos.UsuarioLogadoDTO;
import com.apajac.acolhimento.domain.entities.UsuarioEntity;
import com.apajac.acolhimento.exceptions.NotFoundException;
import com.apajac.acolhimento.exceptions.PermissionException;
import com.apajac.acolhimento.exceptions.UnauthorizedException;
import com.apajac.acolhimento.repositories.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginUsuarioServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private LoginUsuarioServiceImpl loginUsuarioService;

    @Test
    void login_DeveRetornarUsuarioLogadoQuandoCredenciaisValidas() {
        // Dados de entrada
        String login = "joao123";
        String password = "password";
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setId(1L);
        usuarioEntity.setNome("João");
        usuarioEntity.setLogin(login);
        usuarioEntity.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
        usuarioEntity.setStatus(true);
        usuarioEntity.setRoles(Set.of("user"));

        // Configuração do comportamento esperado do repositório
        when(usuarioRepository.getUsuarioByLogin(login)).thenReturn(Optional.of(usuarioEntity));

        // Dados de entrada para o DTO
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setLogin(login);
        loginDTO.setPassword(password);

        // Chama o método a ser testado
        UsuarioLogadoDTO result = loginUsuarioService.login(loginDTO);

        // Verificações
        assertNotNull(result);
        assertEquals(usuarioEntity.getId(), result.getId());
        assertEquals(usuarioEntity.getNome(), result.getNome());
        assertEquals(usuarioEntity.getLogin(), result.getLogin());
        assertEquals(usuarioEntity.getRoles(), result.getRoles());
    }

    @Test
    void login_DeveLancarNotFoundExceptionQuandoUsuarioNaoCadastrado() {
        // Dados de entrada
        String login = "joao123";
        String password = "password";

        // Configuração do comportamento esperado do repositório
        when(usuarioRepository.getUsuarioByLogin(login)).thenReturn(Optional.empty());

        // Dados de entrada para o DTO
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setLogin(login);
        loginDTO.setPassword(password);

        // Verifica se o método lança a exceção esperada
        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                loginUsuarioService.login(loginDTO));

        // Verifica se a exceção contém a mensagem correta
        assertEquals("Usuário não cadastrado.", exception.getMessage());
    }

    @Test
    void login_DeveLancarPermissionExceptionQuandoUsuarioInativo() {
        // Dados de entrada
        String login = "joao123";
        String password = "password";
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setId(1L);
        usuarioEntity.setNome("João");
        usuarioEntity.setLogin(login);
        usuarioEntity.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
        usuarioEntity.setStatus(false);
        usuarioEntity.setRoles(Set.of("user"));

        // Configuração do comportamento esperado do repositório
        when(usuarioRepository.getUsuarioByLogin(login)).thenReturn(Optional.of(usuarioEntity));

        // Dados de entrada para o DTO
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setLogin(login);
        loginDTO.setPassword(password);

        // Verifica se o método lança a exceção esperada
        PermissionException exception = assertThrows(PermissionException.class, () ->
                loginUsuarioService.login(loginDTO));

        // Verifica se a exceção contém a mensagem correta
        assertEquals("Usuário informado com status inativo.", exception.getMessage());
    }

    @Test
    void login_DeveLancarUnauthorizedExceptionQuandoSenhaIncorreta() {
        // Dados de entrada
        String login = "joao123";
        String password = "wrongpassword";
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setId(1L);
        usuarioEntity.setNome("João");
        usuarioEntity.setLogin(login);
        usuarioEntity.setPassword(BCrypt.hashpw("password", BCrypt.gensalt())); // senha correta
        usuarioEntity.setStatus(true);
        usuarioEntity.setRoles(Set.of("user"));

        // Configuração do comportamento esperado do repositório
        when(usuarioRepository.getUsuarioByLogin(login)).thenReturn(Optional.of(usuarioEntity));

        // Dados de entrada para o DTO
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setLogin(login);
        loginDTO.setPassword(password);

        // Verifica se o método lança a exceção esperada
        UnauthorizedException exception = assertThrows(UnauthorizedException.class, () ->
                loginUsuarioService.login(loginDTO));

        // Verifica se a exceção contém a mensagem correta
        assertEquals("Acesso Negado.", exception.getMessage());
    }
}
