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
import java.util.List;

@Component
@AllArgsConstructor
public class AcolhidoMapper {

    private final ModelMapper mapper;

    public List<AcolhidoSimplificadoDTO> convertEntitesToDtos(List<AcolhidoEntity> entities) {
        List<AcolhidoSimplificadoDTO> simplificadoDTOS = new ArrayList<>();
        for (AcolhidoEntity entity : entities) {

            simplificadoDTOS.add(AcolhidoSimplificadoDTO.builder()
                    .nome(entity.getNome())
                    .statusAcolhido(entity.isStatusAcolhido())
                    .mae(entity.getFamiliares().get(0).getNome())
                    .pai(entity.getFamiliares().get(1).getNome())
                    .idade(getIdade(entity.getDataNascimento()))
                    .build());
        }
        return simplificadoDTOS;
    }

    private Integer getIdade(LocalDate dataNascimento) {
        LocalDate hoje = LocalDate.now();
        return Period.between(dataNascimento, hoje).getYears();
    }

    public AcolhidoDTO convertEntityToDto(AcolhidoEntity acolhido) {
        return mapper.map(acolhido, AcolhidoDTO.class);
    }
}
