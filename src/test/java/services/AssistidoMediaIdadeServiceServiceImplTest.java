package services;

import com.apajac.acolhimento.repositories.AssistidoRepository;
import com.apajac.acolhimento.services.AssistidoMediaIdadeServiceServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AssistidoMediaIdadeServiceServiceImplTest {

    @Mock
    private AssistidoRepository assistidoRepository;

    @InjectMocks
    private AssistidoMediaIdadeServiceServiceImpl assistidoMediaIdadeService;

    @Test
    void calcularMediaGeralIdade_DeveRetornarMediaCorreta() {

        List<LocalDate> datasNascimento = Arrays.asList(
                LocalDate.now().minusYears(20),
                LocalDate.now().minusYears(30),
                LocalDate.now().minusYears(40)
        );

        when(assistidoRepository.findAllBirthDates()).thenReturn(datasNascimento);

        Map<String, List<String>> resultado = assistidoMediaIdadeService.calcularMediaGeralIdade();

        assertEquals(List.of("Média Geral"), resultado.get("Labels"));
        assertEquals(List.of("30"), resultado.get("Values"));
    }

    @Test
    void calcularMediaGeralIdade_QuandoNaoHouverDatas_DeveRetornarZero() {

        when(assistidoRepository.findAllBirthDates()).thenReturn(List.of());

        Map<String, List<String>> resultado = assistidoMediaIdadeService.calcularMediaGeralIdade();

        assertEquals(List.of("Média Geral"), resultado.get("Labels"));
        assertEquals(List.of("0"), resultado.get("Values"));
    }

    @Test
    void calcularMediaGeralIdade_ComArredondamento_DeveRetornarValorCorreto() {

        List<LocalDate> datasNascimento = Arrays.asList(
                LocalDate.now().minusYears(25),
                LocalDate.now().minusYears(30),
                LocalDate.now().minusYears(35)
        );

        when(assistidoRepository.findAllBirthDates()).thenReturn(datasNascimento);

        Map<String, List<String>> resultado = assistidoMediaIdadeService.calcularMediaGeralIdade();

        assertEquals(List.of("Média Geral"), resultado.get("Labels"));
        assertEquals(List.of("30"), resultado.get("Values"));
    }
}