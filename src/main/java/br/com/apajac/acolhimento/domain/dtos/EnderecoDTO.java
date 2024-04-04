package br.com.apajac.acolhimento.domain.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class EnderecoDTO {

    private String cep;
    private String endereco;
    private int numero;
    private String bairro;
    private String cidade;
    private String uf;



}
