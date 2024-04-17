package com.apajac.acolhimento.domain.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class EnderecoDTO {
    private String cep;
    private String endereco;
    private String numero;
    private String bairro;
    private String cidade;
    private String UF;
    private String complemento;
}
