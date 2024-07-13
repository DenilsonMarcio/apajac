package com.apajac.acolhimento.mappers;

import com.apajac.acolhimento.domain.dtos.DoadorDTO;
import com.apajac.acolhimento.domain.entities.DoadorEntity;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DoadorMapper {

    private final ModelMapper mapper;

    public DoadorDTO mapEntityToDTO(DoadorEntity Doador)
    {
        return mapper.map(Doador, DoadorDTO.class);
    }

}
