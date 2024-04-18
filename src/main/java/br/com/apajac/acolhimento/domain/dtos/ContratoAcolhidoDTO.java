package br.com.apajac.acolhimento.domain.dtos;

import jakarta.annotation.Nullable;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class ContratoAcolhidoDTO {

    private AcolhidoDTO acolhido;
    private List<FamiliaresDTO> familiares;

}
