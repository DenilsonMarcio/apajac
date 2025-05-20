package com.apajac.acolhimento.services;

import com.apajac.acolhimento.domain.dtos.relatorio.AtivosEInativosDTO;
import com.apajac.acolhimento.repositories.AssistidoRepository;
import com.apajac.acolhimento.services.interfaces.AtivosEInativosService;
import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AtivosEInativosServiceImpl implements AtivosEInativosService {

    private final AssistidoRepository assistidoRepository;

    @Override
    public AtivosEInativosDTO totalAtivosEInativos() {

        List<Tuple> tuples = assistidoRepository.totalAtivosEInativos();

        List<String> labels = new ArrayList<>();
        List<Integer> values = new ArrayList<>();

        for (Tuple tuple : tuples) {
            Boolean statusAssistido = (Boolean) tuple.get("status_assistido");
            if (statusAssistido) {
                labels.add("Ativos");
            }else {
                labels.add("Inativos");
            }

            Number total = (Number) tuple.get("total");
            values.add(total.intValue());
        }

        AtivosEInativosDTO ativosEInativosDTO = new AtivosEInativosDTO();
        ativosEInativosDTO.setLabels(labels.toArray(new String[0]));
        ativosEInativosDTO.setValues(values.toArray(new Integer[0]));

        return ativosEInativosDTO;
    }
}
