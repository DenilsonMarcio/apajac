package com.apajac.acolhimento.domain.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ResponsavelDTO {
    @NotBlank(message = "O campo 'nome' em Responsavel, é obrigatorio")
    private String nome;
    @NotBlank(message = "O campo 'tipo de parentesco' em Responsavel, é obrigatorio")
    private String tipoParentesco;
    @Valid
    @NotEmpty(message = "A lista de contatos em Responsavel, não pode estar vazia")
    private List<ContatoDTO> contatos;
}
