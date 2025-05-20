package com.apajac.acolhimento.services;

import com.apajac.acolhimento.domain.dtos.relatorio.PorSexoDTO;
import com.apajac.acolhimento.repositories.AssistidoRepository;
import com.apajac.acolhimento.services.interfaces.AssistidoPorSexoService;
import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssistidoPorSexoServiceImpl implements AssistidoPorSexoService {

    private final AssistidoRepository assistidoRepository;

    @Override
    public PorSexoDTO totalAssistidoPorSexo() {

        List<Tuple> tuples = assistidoRepository.totalAssistidoPorSexo();

        List<Character> labels = new ArrayList<>();
        List<Integer> values = new ArrayList<>();

        for (Tuple tuple : tuples) {
            labels.add((Character) tuple.get("sexo"));

            Number total = (Number) tuple.get("total");
            values.add(total.intValue());
        }

        PorSexoDTO porSexoDTO = new PorSexoDTO();
        porSexoDTO.setLabels(labels.toArray(new Character[0]));
        porSexoDTO.setValues(values.toArray(new Integer[0]));

        return porSexoDTO;
    }
}



