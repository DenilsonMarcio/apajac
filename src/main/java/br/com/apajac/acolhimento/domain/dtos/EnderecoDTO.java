package br.com.apajac.acolhimento.domain.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class EnderecoDTO {

    private String cep;
    private String endereco;
    private int numero;
    private String bairro;
    private String cidade;
    private String uf;

    @Override
    public String toString() {
        return  "endereco='" + endereco + '\'' +
                ", numero=" + numero +
                ", bairro='" + bairro + '\'' +
                ", cidade='" + cidade + '\'' +
                ", UF='" + uf + '\'' +
                ", cep='" + cep + '\'' +
                '}';
    }

}
