package com.apajac.acolhimento.domain.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "composicao_familiar")
public class ComposicaoFamiliarEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;
    private Integer anoNascimento;
    private String parentesco;
    private String ocupacao;

    @Column(length = 1000)
    private String observacoes;

    @ManyToOne
    @JoinColumn(name = "acolhido_id")
    private AcolhidoEntity acolhido;

}