package gateway;

import com.apajac.acolhimento.domain.dtos.EnderecoDTO;
import com.apajac.acolhimento.exceptions.BusinessException;
import com.apajac.acolhimento.gateway.EnderecoIntegration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EnderecoIntegrationTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private EnderecoIntegration enderecoIntegration;

    @Test
    void enderecoPorCep_DeveRetornarEnderecoDTOQuandoCepValido() {
        // Dados de entrada
        String cep = "12345678";
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("logradouro", "Rua ABC");
        responseBody.put("bairro", "Centro");
        responseBody.put("localidade", "Jacareí");
        responseBody.put("uf", "SP");
        responseBody.put("cep", cep);

        ResponseEntity<Map> responseEntity = new ResponseEntity<>(responseBody, HttpStatus.OK);

        // Configuração do comportamento esperado do RestTemplate
        when(restTemplate.getForEntity(anyString(), eq(Map.class))).thenReturn(responseEntity);

        // Chama o método a ser testado
        EnderecoDTO result = enderecoIntegration.enderecoPorCep(cep);

        // Verificações
        assertNotNull(result);
        assertEquals("Rua ABC", result.getEndereco());
        assertEquals("Centro", result.getBairro());
        assertEquals("Jacareí", result.getCidade());
        assertEquals("SP", result.getUF());
        assertEquals(cep, result.getCep());
    }

    @Test
    void enderecoPorCep_DeveLancarBusinessExceptionQuandoCepDeOutraCidade() {
        // Dados de entrada
        String cep = "12345678";
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("logradouro", "Rua XYZ");
        responseBody.put("bairro", "Centro");
        responseBody.put("localidade", "São Paulo");
        responseBody.put("uf", "SP");
        responseBody.put("cep", cep);

        ResponseEntity<Map> responseEntity = new ResponseEntity<>(responseBody, HttpStatus.OK);

        // Configuração do comportamento esperado do RestTemplate
        when(restTemplate.getForEntity(anyString(), eq(Map.class))).thenReturn(responseEntity);

        // Verifica se o método lança a exceção esperada
        BusinessException exception = assertThrows(BusinessException.class, () ->
                enderecoIntegration.enderecoPorCep(cep));

        // Verifica se a exceção contém a mensagem correta
        assertEquals("CEP informado não pertence ao Municipio de Jacareí", exception.getMessage());
    }

    @Test
    void enderecoPorCep_DeveLancarIllegalArgumentExceptionQuandoCepInvalido() {
        // Dados de entrada
        String cep = "12345678";

        // Configuração do comportamento esperado do RestTemplate
        when(restTemplate.getForEntity(anyString(), eq(Map.class))).thenThrow(new RuntimeException());

        // Verifica se o método lança a exceção esperada
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                enderecoIntegration.enderecoPorCep(cep));

        // Verifica se a exceção contém a mensagem correta
        assertEquals("Não foi possivel encontrar o Endereço para o CEP informado.", exception.getMessage());
    }

    @Test
    void enderecoPorUfCidadeELogradouro_DeveRetornarListaDeEnderecosQuandoDadosValidos() {
        // Dados de entrada
        String uf = "SP";
        String cidade = "Jacareí";
        String logradouro = "Rua ABC";
        List<Map<String, String>> responseBody = List.of(
                Map.of("logradouro", "Rua ABC", "bairro", "Centro", "localidade", "Jacareí", "uf", "SP", "cep", "12345678")
        );

        ResponseEntity<List> responseEntity = new ResponseEntity<>(responseBody, HttpStatus.OK);

        // Configuração do comportamento esperado do RestTemplate
        when(restTemplate.getForEntity(anyString(), eq(List.class))).thenReturn(responseEntity);

        // Chama o método a ser testado
        List<EnderecoDTO> result = enderecoIntegration.enderecoPorUfCidadeELogradouro(uf, cidade, logradouro);

        // Verificações
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Rua ABC", result.get(0).getEndereco());
        assertEquals("Centro", result.get(0).getBairro());
        assertEquals("Jacareí", result.get(0).getCidade());
        assertEquals("SP", result.get(0).getUF());
        assertEquals("12345678", result.get(0).getCep());
    }

    @Test
    void enderecoPorUfCidadeELogradouro_DeveLancarIllegalArgumentExceptionQuandoDadosInvalidos() {
        // Dados de entrada
        String uf = "SP";
        String cidade = "Jacareí";
        String logradouro = "Rua XYZ";

        // Configuração do comportamento esperado do RestTemplate
        when(restTemplate.getForEntity(anyString(), eq(List.class))).thenThrow(new RuntimeException());

        // Verifica se o método lança a exceção esperada
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                enderecoIntegration.enderecoPorUfCidadeELogradouro(uf, cidade, logradouro));

        // Verifica se a exceção contém a mensagem correta
        assertEquals("Não foi possivel encontrar o Endereço com os dados informados.", exception.getMessage());
    }
}
