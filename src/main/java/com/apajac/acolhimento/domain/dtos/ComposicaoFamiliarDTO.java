package com.apajac.acolhimento.domain.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class ComposicaoFamiliarDTO {
    @NotBlank(message = "O campo 'nome' em Composicao Familiar, é obrigatorio")
    private String nome;
    private Integer anoNascimento;
    @NotBlank(message = "O campo 'parentesco' em Composicao Familiar, é obrigatorio")
    private String parentesco;
    private String ocupacao;
    private String observacoes;
}
