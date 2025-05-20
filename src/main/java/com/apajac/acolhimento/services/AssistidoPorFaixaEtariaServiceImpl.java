package com.apajac.acolhimento.services;

import com.apajac.acolhimento.domain.dtos.relatorio.FaixaEtariaDTO;
import com.apajac.acolhimento.repositories.AssistidoRepository;
import com.apajac.acolhimento.services.interfaces.AssistidoPorFaixaEtariaService;
import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssistidoPorFaixaEtariaServiceImpl implements AssistidoPorFaixaEtariaService {

    private final AssistidoRepository assistidoRepository;

    @Override
    public FaixaEtariaDTO totalAssistidoPorFaixaEtaria() {
        List<Tuple> tuples = assistidoRepository.totalAssistidoPorFaixaEtaria();

        List<String> labels = new ArrayList<>();
        List<Integer> values = new ArrayList<>();

        for (Tuple tuple : tuples) {
            labels.add(tuple.get("faixa_etaria").toString());

            Number quantidade = (Number) tuple.get("quantidade");
            values.add(quantidade.intValue());
        }

        FaixaEtariaDTO faixaEtariaDTO = new FaixaEtariaDTO();
        faixaEtariaDTO.setLabels(labels.toArray(new String[0]));
        faixaEtariaDTO.setValues(values.toArray(new Integer[0]));

        return faixaEtariaDTO;
    }
}
