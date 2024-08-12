package com.apajac.acolhimento.domain.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class FamiliarDTO {

    private String nome;
    private List<ContatoDTO> contatos;
    private String ocupacao;
    private String localTrabalho;
    private BigDecimal salario;
    private String vinculoEmpregaticio;
    @NotBlank(message = "O tipo de parentesco é obrigatório")
    private String tipoParentesco;
}
