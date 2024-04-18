package br.com.apajac.acolhimento.domain.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    private ResponsavelDTO responsavel;
    @JsonProperty("composicao_familiar")
    private List<ComposicaoFamiliarDTO> composicaoFamiliar;

}
