package com.apajac.acolhimento.services;

import com.apajac.acolhimento.domain.dtos.relatorio.CadastroMensalDTO;
import com.apajac.acolhimento.domain.enums.MesesEnum;
import com.apajac.acolhimento.repositories.AssistidoRepository;
import com.apajac.acolhimento.services.interfaces.QuantidadeCadastradosMensalService;
import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuantidadeCadastradosMensalServiceImpl implements QuantidadeCadastradosMensalService {

    private final AssistidoRepository assistidoRepository;

    @Override
    public CadastroMensalDTO cadastradosMensal(Integer codAno) {

        List<Tuple> tuples = assistidoRepository.getCadastradosMensal(codAno);

        List<String> labels = new ArrayList<>();
        List<Long> values = new ArrayList<>();

        for (Tuple tuple : tuples) {
            BigDecimal mes = (BigDecimal) tuple.get("mes");
            labels.add(retornaMesCorrespondenteEnum(mes));
            values.add((Long) tuple.get("quantidade_cadastrados"));
        }
        CadastroMensalDTO cadastroMensalDTO = new CadastroMensalDTO();
        cadastroMensalDTO.setLabels(labels.toArray(new String[0]));
        cadastroMensalDTO.setValues(values.toArray(new Long[0]));
        return cadastroMensalDTO;
    }

    private String retornaMesCorrespondenteEnum(BigDecimal mes) {
        MesesEnum[] mesesEnums = MesesEnum.values();
        String mesSelecionado = "";
        for (MesesEnum meses : mesesEnums) {
            if (meses.getValues().equals(mes.intValue())) {
                mesSelecionado = meses.name();
                break;
            }
        }
        return mesSelecionado;
    }
}
