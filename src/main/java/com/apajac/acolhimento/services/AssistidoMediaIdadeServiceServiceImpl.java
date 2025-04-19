package com.apajac.acolhimento.services;

import com.apajac.acolhimento.repositories.AssistidoRepository;
import com.apajac.acolhimento.services.interfaces.AssistidoMediaIdadeService;
import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

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

    @Override
    public Map<String, List<String>> calcularMediaPorFaixaEtaria() {
        List<Tuple> resultados = assistidoRepository.mediaIdadePorFaixaEtaria();

        Map<String, List<String>> resultadoFinal = new LinkedHashMap<>();

        resultadoFinal.put("Labels", resultados.stream()
                .map(t -> t.get("faixa_etaria", String.class))
                .collect(Collectors.toList()));

        resultadoFinal.put("Values", resultados.stream()
                .map(t -> {
                    BigDecimal media = t.get("media_idade", BigDecimal.class);
                    return media != null ? String.format("%.2f", media.doubleValue()) : "0.00";
                })
                .collect(Collectors.toList()));

        return resultadoFinal;
    }
}
