package com.apajac.acolhimento.mappers;

import com.apajac.acolhimento.domain.dtos.AssistidoDTO;
import com.apajac.acolhimento.domain.dtos.AssistidoSimplificadoDTO;
import com.apajac.acolhimento.domain.entities.AssistidoEntity;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
@AllArgsConstructor
public class AssistidoMapper {

    private final ModelMapper mapper;

    public List<AssistidoSimplificadoDTO> convertEntitesToDtos(List<AssistidoEntity> entities, String paramSort) {
        List<AssistidoSimplificadoDTO> simplificadoDTOS = new ArrayList<>();
        for (AssistidoEntity entity : entities) {
            simplificadoDTOS.add(AssistidoSimplificadoDTO.builder()
                    .id(entity.getId())
                    .nome(entity.getNome())
                    .statusAssistido(entity.isStatusAssistido())
                    .responsavel(entity.getResponsavel().getNome())
                    .idade(getIdade(entity.getDataNascimento()))
                    .sexo(entity.getSexo())
                    .build());
        }

        orderByResponsavel(simplificadoDTOS, paramSort);

        return simplificadoDTOS;
    }

    private void orderByResponsavel(List<AssistidoSimplificadoDTO> simplificadoDTOS, String paramSort) {
        String[] split = paramSort.split(":");
        String param = split[0].strip();
        String sort = split[1].strip();

        if (param.equals("responsavel")) {
            if (sort.equals("DESC")) {
                simplificadoDTOS.sort((assistido1, assistido2) -> assistido2.getResponsavel().compareTo(assistido1.getResponsavel()));
            } else if (sort.equals("ASC")) {
                simplificadoDTOS.sort(Comparator.comparing(AssistidoSimplificadoDTO::getResponsavel));
            }
        }
    }

    private Integer getIdade(LocalDate dataNascimento) {
        LocalDate hoje = LocalDate.now();
        return Period.between(dataNascimento, hoje).getYears();
    }

    public AssistidoDTO convertEntityToDto(AssistidoEntity assistido) {
        return mapper.map(assistido, AssistidoDTO.class);
    }
}
