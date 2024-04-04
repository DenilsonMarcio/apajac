package br.com.apajac.acolhimento.domain.entities;

import br.com.apajac.acolhimento.domain.Enum.Familiar;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_parente")
public class ParenteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Familiar familiar;
    private String nome;
    private String tel;
    private String cel;
    private String ocupacao;
    @JsonProperty("local_trabalho")
    private String localTrabalho;
    private Double salario;
    @JsonProperty("vinculo_empregaticio")
    private String vinculoEmpregaticio;

}
