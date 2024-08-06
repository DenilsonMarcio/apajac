package controllers.enderecoController;

import com.apajac.acolhimento.controllers.enderecoController.BuscarEnderecoPorCEPController;
import com.apajac.acolhimento.domain.dtos.EnderecoDTO;
import com.apajac.acolhimento.gateway.EnderecoIntegration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class BuscarEnderecoPorCEPControllerTest {

    @Mock
    private EnderecoIntegration integration;

    @InjectMocks
    private BuscarEnderecoPorCEPController buscarEnderecoPorCEPController;

    @Test
    void dadoCepValido_quandoBuscarEndereco_entaoRetorneEnderecoDTO() {
        // Configuração dos parâmetros de entrada
        String cep = "12345678";

        // Criação de um modelo de DTO
        EnderecoDTO enderecoDTO = new EnderecoDTO();
        enderecoDTO.setCep(cep);
        enderecoDTO.setEndereco("Rua Exemplo");

        // Configuração do comportamento esperado da integração
        Mockito.when(integration.enderecoPorCep(cep)).thenReturn(enderecoDTO);

        // Chama o método a ser testado
        ResponseEntity<EnderecoDTO> response = buscarEnderecoPorCEPController.buscarEndereco(cep);

        // Assert
        // Verifica se o status da resposta e o corpo são os esperados
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(enderecoDTO, response.getBody());
    }

    @Test
    void dadoCepInvalido_quandoBuscarEndereco_entaoRetorneHttpClientErrorException() {
        // Configuração dos parâmetros de entrada
        String cep = "invalid_cep";

        // Configuração do comportamento esperado da integração para lançar uma exceção
        HttpClientErrorException exception = new HttpClientErrorException(HttpStatus.BAD_REQUEST, "CEP inválido");
        Mockito.when(integration.enderecoPorCep(cep)).thenThrow(exception);

        // Verifica se o método lança a exceção esperada
        HttpClientErrorException thrown = assertThrows(HttpClientErrorException.class, () -> {
            buscarEnderecoPorCEPController.buscarEndereco(cep);
        });

        // Verifica o status e a mensagem da exceção
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
        assertEquals("400 400 CEP inválido", thrown.getMessage());
    }

    @Test
    void dadoUfCidadeELogradouroValidos_quandoBuscarEndereco_entaoRetorneListaEnderecoDTO() {
        // Configuração dos parâmetros de entrada
        String uf = "SP";
        String cidade = "São Paulo";
        String logradouro = "Avenida Paulista";

        // Criação de um modelo de DTO
        EnderecoDTO enderecoDTO = new EnderecoDTO();
        enderecoDTO.setUF(uf);
        enderecoDTO.setCidade(cidade);
        enderecoDTO.setEndereco(logradouro);

        List<EnderecoDTO> enderecos = Collections.singletonList(enderecoDTO);

        // Configuração do comportamento esperado da integração
        Mockito.when(integration.enderecoPorUfCidadeELogradouro(uf, cidade, logradouro)).thenReturn(enderecos);

        // Chama o método a ser testado
        ResponseEntity<List<EnderecoDTO>> response = buscarEnderecoPorCEPController.buscarEnderecoPorUfCidadeELogradouro(uf, cidade, logradouro);

        // Assert
        // Verifica se o status da resposta e o corpo são os esperados
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(enderecos, response.getBody());
    }

    @Test
    void dadoUfCidadeELogradouroInvalidos_quandoBuscarEndereco_entaoRetorneHttpClientErrorException() {
        // Configuração dos parâmetros de entrada
        String uf = "InvalidUF";
        String cidade = "InvalidCity";
        String logradouro = "InvalidStreet";

        // Configuração do comportamento esperado da integração para lançar uma exceção
        HttpClientErrorException exception = new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Dados inválidos");
        Mockito.when(integration.enderecoPorUfCidadeELogradouro(uf, cidade, logradouro)).thenThrow(exception);

        // Verifica se o método lança a exceção esperada
        HttpClientErrorException thrown = assertThrows(HttpClientErrorException.class, () -> {
            buscarEnderecoPorCEPController.buscarEnderecoPorUfCidadeELogradouro(uf, cidade, logradouro);
        });

        // Verifica o status e a mensagem da exceção
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
        assertEquals("400 400 Dados inválidos", thrown.getMessage());
    }
}