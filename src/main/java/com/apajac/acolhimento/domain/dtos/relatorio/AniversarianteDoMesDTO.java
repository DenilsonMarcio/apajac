package com.apajac.acolhimento.domain.dtos.relatorio;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class AniversarianteDoMesDTO {
    private Long id;
    private String nome;
    private Long idade;
    private String data_nascimento;
}
