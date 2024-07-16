package com.apajac.acolhimento.domain.entities;

import com.apajac.acolhimento.domain.enums.TipoDoador;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "doador_jonathan")
public class DoadorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;
    private String documento;
    private Float valor;

    @Enumerated(EnumType.STRING)
    private TipoDoador tipo_doador;
    private String como_conheceu;
}
