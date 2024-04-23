package com.apajac.acolhimento.mappers;

import com.apajac.acolhimento.domain.dtos.UsuarioSemSenhaDTO;
import com.apajac.acolhimento.domain.entities.UsuarioEntity;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class UsuarioMapper {
    private final ModelMapper mapper;

    public List<UsuarioSemSenhaDTO> convertEntitesToDtos(List<UsuarioEntity> entities) {
        List<UsuarioSemSenhaDTO> dtos = new ArrayList<>();
        for (UsuarioEntity entity : entities) {
            dtos.add(UsuarioSemSenhaDTO.builder()
                    .id(entity.getId())
                    .nome(entity.getNome())
                    .login(entity.getLogin())
                    .role(entity.getRole())
                    .status(entity.getStatus())
                    .build());
        }
        return dtos;
    }
}
