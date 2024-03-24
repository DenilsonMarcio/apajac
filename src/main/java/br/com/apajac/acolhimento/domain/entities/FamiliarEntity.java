package br.com.apajac.acolhimento.domain.entities;

import br.com.apajac.acolhimento.domain.enums.TipoParentesco;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "familiar")
public class FamiliarEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;
    private String tel;
    private String cel;
    private String ocupacao;
    private String localTrabalho;
    private BigDecimal salario;
    private String vinculoEmpregaticio;

    @Enumerated(EnumType.STRING)
    private TipoParentesco tipoParentesco;

    @ManyToOne
    @JoinColumn(name = "acolhido_id")
    private AcolhidoEntity acolhido;
}