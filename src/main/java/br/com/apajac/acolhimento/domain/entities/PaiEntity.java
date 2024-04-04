package br.com.apajac.acolhimento.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pai_db")
public class PaiEntity {

    @Id
    private String nome;
    private String tel;
    private String ocupacao;
    private String local_trabalho;
    private Double salario;
    private String vinculo_empregaticio;

}
