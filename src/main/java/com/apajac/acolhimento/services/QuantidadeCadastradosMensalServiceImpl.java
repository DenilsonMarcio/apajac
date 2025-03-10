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
    public List<CadastroMensalDTO> cadastradosMensal() {
        List<CadastroMensalDTO> cadastrosMensais = new ArrayList<>();

        List<Tuple> tuples = assistidoRepository.getCadastradosMensal();

        for (Tuple tuple : tuples) {
            CadastroMensalDTO dto = new CadastroMensalDTO();
            BigDecimal mes = (BigDecimal) tuple.get("mes");
            BigDecimal ano = (BigDecimal) tuple.get("ano");
            Long quantidadeCadastrados = (Long) tuple.get("quantidade_cadastrados");

            dto.setMes(retornaMesCorrespondenteEnum(mes));
            dto.setAno(ano.longValue());
            dto.setQuantidadeCadastrados(quantidadeCadastrados.longValue());

            cadastrosMensais.add(dto);
        }

        return cadastrosMensais;
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
