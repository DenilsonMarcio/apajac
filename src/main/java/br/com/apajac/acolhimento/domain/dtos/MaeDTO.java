package br.com.apajac.acolhimento.domain.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class MaeDTO {

    private String nome;
    private String tel;
    private String ocupacao;
    private String local_trabalho;
    private Double salario;
    private String vinculo_empregaticio;

    @Override
    public String toString() {
        return "MaeDTO{" +
                "nome='" + nome + '\'' +
                ", tel='" + tel + '\'' +
                ", ocupacao='" + ocupacao + '\'' +
                ", local_trabalho='" + local_trabalho + '\'' +
                ", salario=" + salario +
                ", vinculo_empregaticio='" + vinculo_empregaticio + '\'' +
                '}';
    }

}
