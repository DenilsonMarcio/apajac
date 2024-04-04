package br.com.apajac.acolhimento.domain.dtos;

import jakarta.persistence.Id;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ResponsavelDTO {

    private String nome;
    private String tel;

    @Override
    public String toString() {
        return "PaiDTO{" +
                "nome='" + nome + '\'' +
                ", tel='" + tel + '\'' +
                '}';
    }

}
