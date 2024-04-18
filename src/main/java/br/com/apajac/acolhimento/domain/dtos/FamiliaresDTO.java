package br.com.apajac.acolhimento.domain.dtos;

import br.com.apajac.acolhimento.domain.Enum.Familiar;
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
@Nullable
public class FamiliaresDTO {

    private String nome;
    private List<String> telefone;
    private String ocupacao;
    @JsonProperty("local_trabalho")
    private String localTrabalho;
    private Double salario;
    @JsonProperty("vinculo_empregaticio")
    private String vinculoEmpregaticio;
    @JsonProperty("tipo_parentesco")
    private Familiar tipoParentesco;
}
