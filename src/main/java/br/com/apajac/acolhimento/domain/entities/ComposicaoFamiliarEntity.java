package br.com.apajac.acolhimento.domain.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Year;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_composicaoFamiliar")
public class ComposicaoFamiliarEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String nome;
    private Year anoNascimento;
    private String parentesco;
    private String ocupacao;
    private String observacoes;

}
