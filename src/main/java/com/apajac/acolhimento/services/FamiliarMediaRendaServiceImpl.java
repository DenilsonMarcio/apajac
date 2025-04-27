package com.apajac.acolhimento.services;

import com.apajac.acolhimento.repositories.FamiliarRepository;
import com.apajac.acolhimento.services.interfaces.FamiliarMediaRendaService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
public class FamiliarMediaRendaServiceImpl implements FamiliarMediaRendaService {

    private final FamiliarRepository familiarRepository;

    public FamiliarMediaRendaServiceImpl(FamiliarRepository familiarRepository) {
        this.familiarRepository = familiarRepository;
    }

    @Override
    public Map<String, List<String>> calcularMediaGeralRenda() {
        List<BigDecimal> rendas = familiarRepository.findAllRendas();
        BigDecimal media = rendas.stream()
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(new BigDecimal(rendas.size()), 2, RoundingMode.HALF_UP);

        Map<String, List<String>> resposta = new HashMap<>();
        resposta.put("Values", List.of(media.toString()));
        resposta.put("Labels", List.of("Média Geral"));
        return resposta;
    }

    @Override
    public Map<String, List<String>> calcularMediaRendaPorAssistido() {
        List<Object[]> dados = familiarRepository.findAllAssistidoIdNomeAndRenda();
        Map<String, List<BigDecimal>> assistidoRendas = new HashMap<>();

        for (Object[] registro : dados) {
            String nome = (String) registro[1];
            BigDecimal renda = (BigDecimal) registro[2];

            if (nome != null && renda != null) {
                assistidoRendas.computeIfAbsent(nome, k -> new ArrayList<>()).add(renda);
            }
        }

        List<String> labels = new ArrayList<>();
        List<String> values = new ArrayList<>();

        assistidoRendas.forEach((nome, listaRendas) -> {
            BigDecimal media = listaRendas.stream()
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    .divide(new BigDecimal(listaRendas.size()), 2, RoundingMode.HALF_UP);
            labels.add(nome);
            values.add(media.toString());
        });

        Map<String, List<String>> resposta = new HashMap<>();
        resposta.put("Values", values);
        resposta.put("Labels", labels);
        return resposta;
    }

    @Override
    public Map<String, List<String>> calcularFaixasDeRenda() {
        List<BigDecimal> rendas = familiarRepository.findAllRendas();

        int ate500 = 0;
        int de501a1000 = 0;
        int de1001a2000 = 0;
        int acima2000 = 0;

        for (BigDecimal renda : rendas) {
            if (renda == null) continue;
            if (renda.compareTo(BigDecimal.valueOf(500)) <= 0) {
                ate500++;
            } else if (renda.compareTo(BigDecimal.valueOf(1000)) <= 0) {
                de501a1000++;
            } else if (renda.compareTo(BigDecimal.valueOf(2000)) <= 0) {
                de1001a2000++;
            } else {
                acima2000++;
            }
        }

        Map<String, List<String>> resposta = new HashMap<>();
        resposta.put("Labels", List.of("Até 500", "De 501 a 1000", "De 1001 a 2000", "Acima de 2000"));
        resposta.put("Values", List.of(
                String.valueOf(ate500),
                String.valueOf(de501a1000),
                String.valueOf(de1001a2000),
                String.valueOf(acima2000)
        ));

        return resposta;
    }
}
