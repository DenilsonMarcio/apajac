package com.apajac.acolhimento.domain.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "contato")
public class ContatoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String contato;

    @ManyToOne
    @JoinColumn(name = "familiar_id")
    private FamiliarEntity familiar;

    @ManyToOne
    @JoinColumn(name = "responsavel_id")
    private ResponsavelEntity responsavel;
}