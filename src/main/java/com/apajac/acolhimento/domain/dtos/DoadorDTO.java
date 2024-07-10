package com.apajac.acolhimento.domain.dtos;

import com.apajac.acolhimento.domain.enums.TipoDoador;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class DoadorDTO
{
    private String nome;
    private String documento;
    private Float valor;
    private TipoDoador tipo_doador;
    private String como_conheceu;
}
