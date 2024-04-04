package br.com.apajac.acolhimento.domain.dtos;

import br.com.apajac.acolhimento.domain.Enum.Familiar;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class ParenteDTO {

    private Familiar familiar;
    private String nome;
    private String tel;
    private String cel;
    private String ocupacao;
    @JsonProperty("local_trabalho")
    private String localTrabalho;
    private Double salario;
    @JsonProperty("vinculo_empregaticio")
    private String vinculoEmpregaticio;
}
