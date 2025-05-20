package services;

import com.apajac.acolhimento.domain.dtos.UsuarioDTO;
import com.apajac.acolhimento.domain.entities.UsuarioEntity;
import com.apajac.acolhimento.exceptions.BusinessException;
import com.apajac.acolhimento.exceptions.NotFoundException;
import com.apajac.acolhimento.repositories.UsuarioRepository;
import com.apajac.acolhimento.services.UsuarioServiceImpl;
import com.apajac.acolhimento.services.interfaces.AuditoriaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceImplTest {

    @Mock
    private UsuarioRepository repository;

    @Mock
    private AuditoriaService auditoria;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @Test
    void cadastrar_DeveSalvarNovoUsuario() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome("João");
        usuarioDTO.setLogin("joao123");
        usuarioDTO.setPassword("password");
        usuarioDTO.setRoles(Set.of("USER"));
        usuarioDTO.setIdResponsavelPeloCadastro(1L);

        UsuarioEntity entity = modelMapper.map(usuarioDTO, UsuarioEntity.class);

        when(repository.save(any(UsuarioEntity.class))).thenReturn(entity);

        // Configuração do comportamento esperado do repositório
        when(repository.findByLogin("joao123")).thenReturn(Optional.empty());

        // Chama o método a ser testado
        usuarioService.cadastrar(usuarioDTO);

        // Verificações
        verify(repository).save(any(UsuarioEntity.class));
        verify(auditoria).inserirDadosDeAuditoria(
                anyLong(),
                anyString(),
                anyString(),
                anyString()
        );
    }

    @Test
    void cadastrar_DeveLancarBusinessExceptionQuandoLoginJaExiste() {
        // Dados de entrada
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome("João");
        usuarioDTO.setLogin("joao123");
        usuarioDTO.setPassword("password");
        usuarioDTO.setRoles(Set.of("USER"));
        usuarioDTO.setIdResponsavelPeloCadastro(1L);

        // Configuração do comportamento esperado do repositório
        when(repository.findByLogin("joao123")).thenReturn(Optional.of(new UsuarioEntity()));

        // Verifica se o método lança a exceção esperada
        BusinessException exception = assertThrows(BusinessException.class, () ->
                usuarioService.cadastrar(usuarioDTO));

        // Verifica se a exceção contém a mensagem correta
        assertEquals("Login já existe.", exception.getMessage());
    }

    @Test
    void remover_DeveRemoverUsuarioExistente() {
        // Dados de entrada
        UsuarioEntity existingUser = new UsuarioEntity();
        existingUser.setId(1L);
        existingUser.setRoles(Set.of("USER"));

        // Configuração do comportamento esperado do repositório
        when(repository.findById(1L)).thenReturn(Optional.of(existingUser));

        // Chama o método a ser testado
        usuarioService.remover(1L);

        // Verificações
        verify(repository).delete(existingUser);
    }

    @Test
    void remover_DeveLancarNotFoundExceptionQuandoUsuarioNaoEncontrado() {
        // Configuração do comportamento esperado do repositório
        when(repository.findById(1L)).thenReturn(Optional.empty());

        // Verifica se o método lança a exceção esperada
        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                usuarioService.remover(1L));

        // Verifica se a exceção contém a mensagem correta
        assertEquals("Usuário não encontrado.", exception.getMessage());
    }

    @Test
    void validDTO_DeveLancarIllegalArgumentExceptionQuandoDTOForNulo() {
        // Verifica se o método lança a exceção esperada
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                usuarioService.cadastrar(null));

        // Verifica se a exceção contém a mensagem correta
        assertEquals("O DTO do usuário não pode ser nulo.", exception.getMessage());
    }
    @Test
    void validDTO_DeveLancarIllegalArgumentExceptionQuandoNomeForNulo() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setLogin("joao123");
        usuarioDTO.setPassword("newpassword");
        usuarioDTO.setRoles(Set.of("USER"));
        usuarioDTO.setIdResponsavelPeloCadastro(1L);

        // Verifica se o método lança a exceção esperada
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                usuarioService.cadastrar(usuarioDTO));

        // Verifica se a exceção contém a mensagem correta
        assertEquals("O Nome do usuário não pode ser nulo/vazio.", exception.getMessage());
    }

    @Test
    void validDTO_DeveLancarIllegalArgumentExceptionQuandoNomeForVazio() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome(" ");
        usuarioDTO.setLogin("joao123");
        usuarioDTO.setPassword("newpassword");
        usuarioDTO.setRoles(Set.of("USER"));
        usuarioDTO.setIdResponsavelPeloCadastro(1L);

        // Verifica se o método lança a exceção esperada
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                usuarioService.cadastrar(usuarioDTO));

        // Verifica se a exceção contém a mensagem correta
        assertEquals("O Nome do usuário não pode ser nulo/vazio.", exception.getMessage());
    }

    @Test
    void validDTO_DeveLancarIllegalArgumentExceptionQuandoLoginForNulo() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome("João");
        usuarioDTO.setPassword("newpassword");
        usuarioDTO.setRoles(Set.of("USER"));
        usuarioDTO.setIdResponsavelPeloCadastro(1L);

        // Verifica se o método lança a exceção esperada
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                usuarioService.cadastrar(usuarioDTO));

        // Verifica se a exceção contém a mensagem correta
        assertEquals("O Login do usuário não pode ser nulo/vazio.", exception.getMessage());
    }

    @Test
    void validDTO_DeveLancarIllegalArgumentExceptionQuandoLoginForVazio() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome("João");
        usuarioDTO.setLogin(" ");
        usuarioDTO.setPassword("newpassword");
        usuarioDTO.setRoles(Set.of("USER"));
        usuarioDTO.setIdResponsavelPeloCadastro(1L);

        // Verifica se o método lança a exceção esperada
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                usuarioService.cadastrar(usuarioDTO));

        // Verifica se a exceção contém a mensagem correta
        assertEquals("O Login do usuário não pode ser nulo/vazio.", exception.getMessage());
    }

    @Test
    void validDTO_DeveLancarIllegalArgumentExceptionQuandoSenhaForNula() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome("João");
        usuarioDTO.setLogin("joao123");
        usuarioDTO.setRoles(Set.of("USER"));
        usuarioDTO.setIdResponsavelPeloCadastro(1L);

        // Verifica se o método lança a exceção esperada
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                usuarioService.cadastrar(usuarioDTO));

        // Verifica se a exceção contém a mensagem correta
        assertEquals("A Senha do usuário não pode ser nula/vazia.", exception.getMessage());
    }

    @Test
    void validDTO_DeveLancarIllegalArgumentExceptionQuandoSenhaForVazia() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome("João");
        usuarioDTO.setLogin("joao123");
        usuarioDTO.setPassword(" ");
        usuarioDTO.setRoles(Set.of("USER"));
        usuarioDTO.setIdResponsavelPeloCadastro(1L);

        // Verifica se o método lança a exceção esperada
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                usuarioService.cadastrar(usuarioDTO));

        // Verifica se a exceção contém a mensagem correta
        assertEquals("A Senha do usuário não pode ser nula/vazia.", exception.getMessage());
    }

    @Test
    void validDTO_DeveLancarIllegalArgumentExceptionQuandoSenhaForCurta() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome("João");
        usuarioDTO.setLogin("joao123");
        usuarioDTO.setPassword("12345");
        usuarioDTO.setRoles(Set.of("USER"));
        usuarioDTO.setIdResponsavelPeloCadastro(1L);

        // Verifica se o método lança a exceção esperada
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                usuarioService.cadastrar(usuarioDTO));

        // Verifica se a exceção contém a mensagem correta
        assertEquals("A Senha do usuário não pode ser menor que 6 dígitos.", exception.getMessage());
    }

    @Test
    void validDTO_DeveLancarIllegalArgumentExceptionQuandoPapeisForemNulos() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome("João");
        usuarioDTO.setLogin("joao123");
        usuarioDTO.setPassword("newpassword");
        usuarioDTO.setIdResponsavelPeloCadastro(1L);

        // Verifica se o método lança a exceção esperada
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                usuarioService.cadastrar(usuarioDTO));

        // Verifica se a exceção contém a mensagem correta
        assertEquals("Os Papéis do usuário não podem ser nulos/vazios.", exception.getMessage());
    }

    @Test
    void validDTO_DeveLancarIllegalArgumentExceptionQuandoPapeisForemVazios() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome("João");
        usuarioDTO.setLogin("joao123");
        usuarioDTO.setPassword("newpassword");
        usuarioDTO.setRoles(Set.of());
        usuarioDTO.setIdResponsavelPeloCadastro(1L);

        // Verifica se o método lança a exceção esperada
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                usuarioService.cadastrar(usuarioDTO));

        // Verifica se a exceção contém a mensagem correta
        assertEquals("Os Papéis do usuário não podem ser nulos/vazios.", exception.getMessage());
    }

    @Test
    void validDTO_DeveLancarIllegalArgumentExceptionQuandoResponsavelForNulo() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome("João");
        usuarioDTO.setLogin("joao123");
        usuarioDTO.setPassword("newpassword");
        usuarioDTO.setRoles(Set.of("USER"));

        // Verifica se o método lança a exceção esperada
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                usuarioService.cadastrar(usuarioDTO));

        // Verifica se a exceção contém a mensagem correta
        assertEquals("O ID de Responsável não pode ser nulo.", exception.getMessage());
    }

    @Test
    void buscarUsuariosPorNome_DeveRetornarUsuariosQuandoExistirem() {
        // Dados de entrada
        String nome = "João";
        Pageable pageable = Pageable.unpaged();
        Page<UsuarioEntity> expectedPage = mock(Page.class);

        // Configuração do comportamento esperado do repositório
        when(repository.findAllByNomeContainingIgnoreCase(nome, pageable)).thenReturn(expectedPage);

        // Chama o método a ser testado
        Page<UsuarioEntity> result = usuarioService.buscarUsuariosPorNome(nome, pageable);

        // Verificações
        assertEquals(expectedPage, result);
    }
}
