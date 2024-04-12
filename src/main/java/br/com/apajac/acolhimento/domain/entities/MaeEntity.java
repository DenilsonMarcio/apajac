package br.com.apajac.acolhimento.domain.entities;

import br.com.apajac.acolhimento.domain.Enum.Familiar;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_mae")
public class MaeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String nome;
    private List<String> tel;
    private String ocupacao;
    @JsonProperty("local_trabalho")
    private String localTrabalho;
    private Double salario;
    @JsonProperty("vinculo_empregaticio")
    private String vinculoEmpregaticio;

}
