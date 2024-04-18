package br.com.apajac.acolhimento.domain.dtos;

import br.com.apajac.acolhimento.domain.Enum.Familiar;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class ResponsavelDTO {

    private String nome;
    private List<String> telefone;
    @JsonProperty("tipo_parentesco")
    private Familiar tipoParentesco;

}
