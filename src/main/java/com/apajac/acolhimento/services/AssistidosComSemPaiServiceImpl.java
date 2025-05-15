package com.apajac.acolhimento.services;

import com.apajac.acolhimento.repositories.AssistidoRepository;
import com.apajac.acolhimento.services.interfaces.AssistidosComSemPaiService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AssistidosComSemPaiServiceImpl implements AssistidosComSemPaiService {

    @Autowired
    private AssistidoRepository assistidoRepository;

    @Override
    public Map<String, List<String>> totalAssistidosComSemPai() {
        List<Object[]> resultado = assistidoRepository.getAlunosComSemPai();

        List<String> labels = new ArrayList<>();
        List<String> values = new ArrayList<>();

        for (Object[] row : resultado) {
            labels.add((String) row[0]);
            values.add(String.valueOf(row[1]));
        }

        Map<String, List<String>> response = new LinkedHashMap<>();
        response.put("labels", labels);
        response.put("values", values);

        return response;
    }

}
