package com.apajac.acolhimento.domain.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class DoadorDTO
{
    private Long id;
    private String nome;
    private String documento;
    private Float valor;
    private String tipo_doador;
    private String como_conheceu;
}
