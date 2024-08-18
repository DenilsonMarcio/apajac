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

    private String nome= "Nada Informado";

    @OneToMany(mappedBy = "familiar", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<ContatoEntity> contatos;

    private String ocupacao= "Nada Informado";
    private String localTrabalho= "Nada Informado";
    private BigDecimal salario = new BigDecimal(0);
    private String vinculoEmpregaticio = "Nada Informado";

    @Enumerated(EnumType.STRING)
    private TipoParentesco tipoParentesco;

    @ManyToOne
    @JoinColumn(name = "assistido_id")
    private AssistidoEntity assistido;
}