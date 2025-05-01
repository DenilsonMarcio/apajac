package com.apajac.acolhimento.services;

import com.apajac.acolhimento.repositories.AssistidoRepository;
import com.apajac.acolhimento.services.interfaces.AssistidosInstituicaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AssistidosInstituicaoServiceImpl implements AssistidosInstituicaoService {

    private final AssistidoRepository assistidoRepository;

    @Override
    public Map<String, List<String>> totalPorInstituicoes() {
        List<Object[]> resultados = assistidoRepository.getInstituicoes();

        List<String> labels = new ArrayList<>();
        List<String> values = new ArrayList<>();

        for (Object[] row : resultados) {
            labels.add((String) row[0]);
            values.add(String.valueOf(row[1]));
        }

        return Map.of(
                "Labels", labels,
                "Values", values
        );
    }
}
