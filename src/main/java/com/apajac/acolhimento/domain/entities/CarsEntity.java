package com.apajac.acolhimento.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "cars")
public class CarsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Double pontuacao;

    private LocalDate realizadoEm;

    @ManyToOne
    @JoinColumn(name = "assistido_id")
    private AssistidoEntity assistido;

    @OneToMany(mappedBy = "cars", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<RespostaCarsEntity> respostas;
}
