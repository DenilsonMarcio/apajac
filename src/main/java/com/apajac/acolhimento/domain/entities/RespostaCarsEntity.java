package com.apajac.acolhimento.domain.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "respostas_cars")
public class RespostaCarsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer pergunta;

    private Double resposta;

    @Column(length = 1000)
    private String observacoes;

    @ManyToOne
    @JoinColumn(name = "cars_id")
    private CarsEntity cars;

}
