package br.com.apajac.acolhimento.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "acolhido")
public class AcolhidoEntity {

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

    @Embedded
    private Endereco endereco;

    @Column(length = 1000)
    private String observacoes;

    @OneToMany(mappedBy = "acolhido", cascade = CascadeType.ALL)
    private List<FamiliarEntity> familiares;

    @OneToMany(mappedBy = "acolhido", cascade = CascadeType.ALL)
    private List<ComposicaoFamiliarEntity> composicaoFamiliar;
}
