package services;

import com.apajac.acolhimento.domain.dtos.UsuarioDTO;
import com.apajac.acolhimento.domain.entities.UsuarioEntity;
import com.apajac.acolhimento.domain.enums.AuditoriaEnum;
import com.apajac.acolhimento.exceptions.BusinessException;
import com.apajac.acolhimento.exceptions.NotFoundException;
import com.apajac.acolhimento.repositories.UsuarioRepository;
import com.apajac.acolhimento.services.AtualizarDadosUsuarioServiceImpl;
import com.apajac.acolhimento.services.interfaces.AuditoriaService;
import com.apajac.acolhimento.services.interfaces.UsuarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.Optional;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class AtualizarDadosUsuarioServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private AuditoriaService auditoria;

    @InjectMocks
    private AtualizarDadosUsuarioServiceImpl atualizarDadosUsuarioService;

    @Test
    void updateDadosUsuario_DeveAtualizarUsuario() {
        // Dados de entrada
        Long userId = 1L;
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome("João");
        usuarioDTO.setLogin("joao123");
        usuarioDTO.setRoles(Set.of("user"));
        usuarioDTO.setPassword("newPassword");
        usuarioDTO.setIdResponsavelPeloCadastro(2L);

        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setId(userId);
        usuarioEntity.setNome("João Antigo");
        usuarioEntity.setLogin("joao_old");
        usuarioEntity.setRoles(Set.of("user"));

        // Configuração do comportamento esperado do repositório
        Mockito.when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuarioEntity));
        Mockito.when(usuarioRepository.save(usuarioEntity)).thenReturn(usuarioEntity);

        // Chama o método a ser testado
        atualizarDadosUsuarioService.updateDadosUsuario(userId, usuarioDTO);

        // Verificações
        Mockito.verify(usuarioRepository).save(usuarioEntity);
        Mockito.verify(auditoria).inserirDadosDeAuditoria(
                usuarioDTO.getIdResponsavelPeloCadastro(),
                AuditoriaEnum.UPDATED.getValues(),
                UsuarioService.class.getSimpleName(),
                usuarioEntity.toString()
        );
    }

    @Test
    void updateDadosUsuario_DeveLancarNotFoundExceptionQuandoUsuarioNaoExistir() {
        // Dados de entrada
        Long userId = 1L;
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome("João");
        usuarioDTO.setLogin("joao123");
        usuarioDTO.setRoles(Set.of("user"));
        usuarioDTO.setPassword("newPassword");
        usuarioDTO.setIdResponsavelPeloCadastro(2L);

        // Configuração do comportamento esperado do repositório
        Mockito.when(usuarioRepository.findById(userId)).thenReturn(Optional.empty());

        // Verifica se o método lança a exceção esperada
        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                atualizarDadosUsuarioService.updateDadosUsuario(userId, usuarioDTO));

        // Verifica se a exceção contém a mensagem correta
        assertEquals("Usuário não encontrado.", exception.getMessage());
    }

    @Test
    void updateDadosUsuario_DeveLancarBusinessExceptionQuandoUsuarioForRoot() {
        // Dados de entrada
        Long userId = 1L;
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome("João");
        usuarioDTO.setLogin("joao123");
        usuarioDTO.setRoles(Set.of("user"));
        usuarioDTO.setPassword("newPassword");
        usuarioDTO.setIdResponsavelPeloCadastro(2L);

        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setId(userId);
        usuarioEntity.setNome("João Antigo");
        usuarioEntity.setLogin("joao_old");
        usuarioEntity.setRoles(Set.of("root"));

        // Configuração do comportamento esperado do repositório
        Mockito.when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuarioEntity));

        // Verifica se o método lança a exceção esperada
        BusinessException exception = assertThrows(BusinessException.class, () ->
                atualizarDadosUsuarioService.updateDadosUsuario(userId, usuarioDTO));

        // Verifica se a exceção contém a mensagem correta
        assertEquals("Usuário ROOT não pode ser alterado.", exception.getMessage());
    }

    @Test
    void updateDadosUsuario_DeveCriptografarSenhaSeForFornecida() {
        // Dados de entrada
        Long userId = 1L;
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome("João");
        usuarioDTO.setLogin("joao123");
        usuarioDTO.setRoles(Set.of("user"));
        usuarioDTO.setPassword("newPassword");
        usuarioDTO.setIdResponsavelPeloCadastro(2L);

        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setId(userId);
        usuarioEntity.setNome("João Antigo");
        usuarioEntity.setLogin("joao_old");
        usuarioEntity.setRoles(Set.of("user"));

        // Configuração do comportamento esperado do repositório
        Mockito.when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuarioEntity));
        Mockito.when(usuarioRepository.save(usuarioEntity)).thenReturn(usuarioEntity);

        // Chama o método a ser testado
        atualizarDadosUsuarioService.updateDadosUsuario(userId, usuarioDTO);

        // Verificações
        Mockito.verify(usuarioRepository).save(usuarioEntity);
        assertTrue(BCrypt.checkpw("newPassword", usuarioEntity.getPassword()));
    }

    @Test
    void updateDadosUsuario_NaoDeveCriptografarSenhaSeNaoForFornecida() {
        // Dados de entrada
        Long userId = 1L;
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome("João");
        usuarioDTO.setLogin("joao123");
        usuarioDTO.setRoles(Set.of("user"));
        usuarioDTO.setPassword("");
        usuarioDTO.setIdResponsavelPeloCadastro(2L);

        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setId(userId);
        usuarioEntity.setNome("João Antigo");
        usuarioEntity.setLogin("joao_old");
        usuarioEntity.setRoles(Set.of("user"));
        usuarioEntity.setPassword("oldPasswordHash");

        // Configuração do comportamento esperado do repositório
        Mockito.when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuarioEntity));
        Mockito.when(usuarioRepository.save(usuarioEntity)).thenReturn(usuarioEntity);

        // Chama o método a ser testado
        atualizarDadosUsuarioService.updateDadosUsuario(userId, usuarioDTO);

        // Verificações
        Mockito.verify(usuarioRepository).save(usuarioEntity);
        assertEquals("oldPasswordHash", usuarioEntity.getPassword());
    }
}