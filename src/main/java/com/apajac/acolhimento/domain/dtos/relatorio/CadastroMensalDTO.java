package com.apajac.acolhimento.domain.dtos.relatorio;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CadastroMensalDTO {
    private String mes;
    private Long ano;
    private Long quantidadeCadastrados;
}
