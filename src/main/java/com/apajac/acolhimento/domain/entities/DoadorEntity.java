package com.apajac.acolhimento.domain.entities;

import com.apajac.acolhimento.domain.enums.TipoDoador;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "doador_leandro")
public class DoadorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;
    private String documento;
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    private TipoDoador tipoDoador;
    private String comoConheceu;

    private Long idResponsavelPeloCadastro;


}