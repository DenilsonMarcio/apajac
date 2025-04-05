package com.apajac.acolhimento.services;

import com.apajac.acolhimento.domain.dtos.relatorio.AniversarianteDoMesDTO;
import com.apajac.acolhimento.repositories.AssistidoRepository;
import com.apajac.acolhimento.services.interfaces.AniversarianteDoMesService;
import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AniversarianteDoMesServiceImpl implements AniversarianteDoMesService {

    private final AssistidoRepository assistidoRepository;

    @Override
    public List<AniversarianteDoMesDTO> aniversariantesDoMes(Integer codMes) {

        List<AniversarianteDoMesDTO> aniversariantes = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        List<Tuple> tuples = assistidoRepository.getAniversariantesDoMes(codMes);

        for (Tuple tuple : tuples) {
            AniversarianteDoMesDTO dto = new AniversarianteDoMesDTO();
            dto.setId((Long) tuple.get("id"));
            dto.setNome(tuple.get("nome").toString());

            Date sqlDate = (Date) tuple.get("data_nascimento");
            dto.setData_nascimento(sqlDate.toLocalDate().format(formatter));

            Number idade = (Number) tuple.get("idade");
            dto.setIdade(idade.longValue());

            aniversariantes.add(dto);
        }

        return aniversariantes;
    }
}


