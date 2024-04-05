package br.com.apajac.acolhimento.domain.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import java.time.Year;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class ComposicaoFamiliarDTO {

    private String nome;
    @JsonProperty("ano_nascimento")
    private Year anoNascimento;
    private String parentesco;
    private String ocupacao;
    private String observacoes;


}
