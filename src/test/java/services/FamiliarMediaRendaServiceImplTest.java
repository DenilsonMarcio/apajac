package services;

import com.apajac.acolhimento.repositories.FamiliarRepository;
import com.apajac.acolhimento.services.FamiliarMediaRendaServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FamiliarMediaRendaServiceImplTest {

    @Mock
    private FamiliarRepository familiarRepository;

    @InjectMocks
    private FamiliarMediaRendaServiceImpl familiarMediaRendaService;

    @Test
    void calcularMediaGeralRenda_DeveRetornarMediaCorreta() {
        List<BigDecimal> rendas = Arrays.asList(
                BigDecimal.valueOf(500),
                BigDecimal.valueOf(1500),
                BigDecimal.valueOf(2500)
        );

        when(familiarRepository.findAllRendas()).thenReturn(rendas);

        Map<String, List<String>> resultado = familiarMediaRendaService.calcularMediaGeralRenda();

        assertEquals(List.of("Média Geral"), resultado.get("Labels"));
        assertEquals(List.of("1500.00"), resultado.get("Values"));
    }

    @Test
    void calcularMediaRendaPorAssistido_DeveRetornarMediaPorNome() {
        List<Object[]> dados = Arrays.asList(
                new Object[]{1L, "Leandro", BigDecimal.valueOf(1000)},
                new Object[]{1L, "Leandro", BigDecimal.valueOf(500)},
                new Object[]{2L, "Karol", BigDecimal.valueOf(1500)}
        );

        when(familiarRepository.findAllAssistidoIdNomeAndRenda()).thenReturn(dados);

        Map<String, List<String>> resultado = familiarMediaRendaService.calcularMediaRendaPorAssistido();

        assertEquals(List.of("Leandro", "Karol"), resultado.get("Labels"));
        assertEquals(List.of("750.00", "1500.00"), resultado.get("Values"));
    }

    @Test
    void calcularFaixasDeRenda_DeveContarCorretamente() {
        List<BigDecimal> rendas = Arrays.asList(
                BigDecimal.valueOf(400),
                BigDecimal.valueOf(600),
                BigDecimal.valueOf(1500),
                BigDecimal.valueOf(2500),
                BigDecimal.valueOf(800)
        );

        when(familiarRepository.findAllRendas()).thenReturn(rendas);

        Map<String, List<String>> resultado = familiarMediaRendaService.calcularFaixasDeRenda();

        assertEquals(
                List.of("Até 500", "De 501 a 1000", "De 1001 a 2000", "Acima de 2000"),
                resultado.get("Labels")
        );
        assertEquals(
                List.of("1", "2", "1", "1"),
                resultado.get("Values")
        );
    }

    @Test
    void calcularMediaGeralRenda_QuandoNaoHouverRendas_DeveLancarExcecao() {
        when(familiarRepository.findAllRendas()).thenReturn(List.of());

        try {
            familiarMediaRendaService.calcularMediaGeralRenda();
        } catch (ArithmeticException e) {
            assertEquals("/ by zero", e.getMessage());
        }
    }
}
