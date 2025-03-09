package com.apajac.acolhimento.services;

import com.apajac.acolhimento.domain.dtos.relatorio.PorIdadeDTO;
import com.apajac.acolhimento.repositories.AssistidoRepository;
import com.apajac.acolhimento.services.interfaces.AssistidosPorIdadeService;
import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssistidosPorIdadeServiceImpl implements AssistidosPorIdadeService {

    private final AssistidoRepository assistidoRepository;

    @Override
    public PorIdadeDTO totalAssistidosPorIdade() {
        List<Tuple> tuples = assistidoRepository.totalAssistidosPorIdade();

        List<String> labels = new ArrayList<>();
        List<Integer> values = new ArrayList<>();

        for (Tuple tuple : tuples) {
            Number idade = (Number) tuple.get("idade");
            labels.add(idade + " anos");

            Number total = (Number) tuple.get("total");
            values.add(total.intValue());
        }

        PorIdadeDTO porIdadeDTO = new PorIdadeDTO();
        porIdadeDTO.setLabels(labels.toArray(new String[0]));
        porIdadeDTO.setValues(values.toArray(new Integer[0]));

        return porIdadeDTO;
    }
}
