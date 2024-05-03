package com.apajac.acolhimento.mappers;

import com.apajac.acolhimento.domain.dtos.AcolhidoDTO;
import com.apajac.acolhimento.domain.dtos.AcolhidoSimplificadoDTO;
import com.apajac.acolhimento.domain.entities.AcolhidoEntity;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Component
@AllArgsConstructor
public class AcolhidoMapper {

    private final ModelMapper mapper;

    public List<AcolhidoSimplificadoDTO> convertEntitesToDtos(List<AcolhidoEntity> entities, String paramSort) {
        List<AcolhidoSimplificadoDTO> simplificadoDTOS = new ArrayList<>();
        for (AcolhidoEntity entity : entities) {
            simplificadoDTOS.add(AcolhidoSimplificadoDTO.builder()
                    .id(entity.getId())
                    .nome(entity.getNome())
                    .statusAcolhido(entity.isStatusAcolhido())
                    .responsavel(entity.getResponsavel().getNome())
                    .idade(getIdade(entity.getDataNascimento()))
                    .build());
        }

        orderByResponsavel(simplificadoDTOS, paramSort);

        return simplificadoDTOS;
    }

    private void orderByResponsavel(List<AcolhidoSimplificadoDTO> simplificadoDTOS, String paramSort) {
        String[] split = paramSort.split(":");
        String param = split[0].strip();
        String sort = split[1].strip();

        if (param.equals("responsavel")) {
            if (sort.equals("DESC")) {
                Collections.sort(simplificadoDTOS, new Comparator<AcolhidoSimplificadoDTO>() {
                    @Override
                    public int compare(AcolhidoSimplificadoDTO acolhido1, AcolhidoSimplificadoDTO acolhido2) {
                        return acolhido2.getResponsavel().compareTo(acolhido1.getResponsavel());
                    }
                });
            } else if (sort.equals("ASC")) {
                Collections.sort(simplificadoDTOS, new Comparator<AcolhidoSimplificadoDTO>() {
                    @Override
                    public int compare(AcolhidoSimplificadoDTO acolhido1, AcolhidoSimplificadoDTO acolhido2) {
                        return acolhido1.getResponsavel().compareTo(acolhido2.getResponsavel());
                    }
                });
            }
        }
    }

    private Integer getIdade(LocalDate dataNascimento) {
        LocalDate hoje = LocalDate.now();
        return Period.between(dataNascimento, hoje).getYears();
    }

    public AcolhidoDTO convertEntityToDto(AcolhidoEntity acolhido) {
        return mapper.map(acolhido, AcolhidoDTO.class);
    }
}
