package com.apajac.acolhimento.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "assistido")
public class AssistidoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;
    private LocalDate dataNascimento;
    private String escolaridade;
    private String escola;
    private String telEscola;
    private boolean cadastroInstituicao;
    private String instituicao;
    private String encaminhadoPara;
    private String quemIndicouApajac;
    private String informacoesFornecidasPor;

    private Long idResponsavelPeloCadastro;
    private LocalDate cadastradoEm;

    private boolean statusAcolhido = Boolean.TRUE;

    @Embedded
    private Endereco endereco;

    @Column(length = 1000)
    private String observacoes;

    @OneToMany(mappedBy = "assistido", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<FamiliarEntity> familiares;

    @OneToMany(mappedBy = "assistido", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<ComposicaoFamiliarEntity> composicaoFamiliar;

    @OneToOne(mappedBy = "assistido", cascade = CascadeType.ALL)
    @ToString.Exclude
    private ResponsavelEntity responsavel;
}
