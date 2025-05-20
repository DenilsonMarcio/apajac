package com.apajac.acolhimento.domain.dtos;

import lombok.*;
import jakarta.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ContatoDTO {

    @NotBlank(message = "O campo 'contato' em Contato, é obrigatorio")
    private String contato;
}
