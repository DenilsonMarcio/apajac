package com.apajac.acolhimento.mappers;

import com.apajac.acolhimento.domain.dtos.DoadorDTO;
import com.apajac.acolhimento.domain.entities.DoadorEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class DoadorMapper
{
        public List<DoadorDTO> convertEntitiesToDtos(List<DoadorEntity> entities)
    {
        List<DoadorDTO> DTOS = new ArrayList<>();
        for (DoadorEntity entity : entities)
        {
            DTOS.add(DoadorDTO.builder()
                    .id(entity.getId())
                    .nome(entity.getNome())
                    .documento(entity.getDocumento())
                    .valor(entity.getValor())
                    .tipo_doador(entity.getTipo_doador())
                    .como_conheceu(entity.getComo_conheceu())
                    .build());
        }
        return DTOS;
    }
}
