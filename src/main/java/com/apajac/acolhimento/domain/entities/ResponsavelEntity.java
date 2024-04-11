package com.apajac.acolhimento.domain.entities;

import com.apajac.acolhimento.domain.enums.TipoParentesco;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "responsavel")
public class ResponsavelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;

    @Enumerated(EnumType.STRING)
    private TipoParentesco tipoParentesco;

    @OneToMany(mappedBy = "responsavel", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<ContatoEntity> contatos;

    @OneToOne
    @JoinColumn(name = "acolhido_id")
    private AcolhidoEntity acolhido;
}