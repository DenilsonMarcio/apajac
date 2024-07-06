package com.apajac.acolhimento.domain.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "doador")
public class DoadorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;
    private String documento;
    private Float valor;
    private String tipo_doador;
    private String como_conheceu;
}
