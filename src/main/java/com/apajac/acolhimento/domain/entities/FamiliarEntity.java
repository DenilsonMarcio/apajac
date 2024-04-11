package com.apajac.acolhimento.domain.entities;

import com.apajac.acolhimento.domain.enums.TipoParentesco;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "familiar")
public class FamiliarEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;

    @OneToMany(mappedBy = "familiar", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<ContatoEntity> contatos;

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