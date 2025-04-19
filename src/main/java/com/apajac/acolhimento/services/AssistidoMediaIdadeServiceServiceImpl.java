package com.apajac.acolhimento.services;

import com.apajac.acolhimento.repositories.AssistidoRepository;
import com.apajac.acolhimento.services.interfaces.AssistidoMediaIdadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AssistidoMediaIdadeServiceServiceImpl implements AssistidoMediaIdadeService {

    private final AssistidoRepository assistidoRepository;


    @Override
    public Map<String, List<String>> calcularMediaGeralIdade() {
        List<LocalDate> datas = assistidoRepository.findAllBirthDates();

        int media = (int) datas.stream()
                .mapToInt(data -> Period.between(data, LocalDate.now()).getYears())
                .average()
                .orElse(0);

        return Map.of(
                "Labels", List.of("Média Geral"),
                "Values", List.of(String.valueOf(media))
        );
    }
}
