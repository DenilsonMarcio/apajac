package com.apajac.acolhimento.domain.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class EnderecoDTO {

    private String cep;
    @NotBlank(message = "O campo 'endereco' em Endereço, é obrigatorio")
    private String endereco;
    @NotBlank(message = "O campo 'numero' em Endereço, é obrigatorio")
    private String numero;
    @NotBlank(message = "O campo 'bairro' em Endereço, é obrigatorio")
    private String bairro;
    @NotBlank(message = "O campo 'cidade' em Endereço, é obrigatorio")
    private String cidade;
    @NotBlank(message = "O campo 'UF' em Endereço, é obrigatorio")
    private String UF;
    private String complemento;
}
