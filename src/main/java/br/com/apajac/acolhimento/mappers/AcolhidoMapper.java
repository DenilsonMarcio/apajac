package br.com.apajac.acolhimento.mappers;

import br.com.apajac.acolhimento.domain.dtos.AcolhidoDTO;
import br.com.apajac.acolhimento.domain.entities.AcolhidoEntity;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class AcolhidoMapper {

    private final ModelMapper mapper;
    public List<AcolhidoDTO> convertEntitesToDtos(List<AcolhidoEntity> entities) {
        return mapper.map(entities,  new TypeToken<List<AcolhidoDTO>>() {}.getType());
    }

    public AcolhidoDTO convertEntityToDto(AcolhidoEntity acolhido) {
        return mapper.map(acolhido, AcolhidoDTO.class);
    }
}
