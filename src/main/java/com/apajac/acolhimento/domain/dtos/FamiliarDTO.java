package com.apajac.acolhimento.domain.dtos;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class FamiliarDTO {
    private String nome;
    private List<ContatoDTO> contatos;
    private String ocupacao;
    private String localTrabalho;
    private BigDecimal salario;
    private String vinculoEmpregaticio;
    private String tipoParentesco;
}
