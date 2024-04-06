package br.com.apajac.acolhimento.domain.dtos;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class FamiliarDTO {
    private String nome;
    private String tel;
    private String cel;
    private String ocupacao;
    private String localTrabalho;
    private BigDecimal salario;
    private String vinculoEmpregaticio;
    private String tipoParentesco;
}
