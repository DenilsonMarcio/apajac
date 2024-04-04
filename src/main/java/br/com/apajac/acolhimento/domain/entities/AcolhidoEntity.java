package br.com.apajac.acolhimento.domain.entities;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_acolhido")
public class AcolhidoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_cep", referencedColumnName = "cep")
    private EnderecoEntity enderecoEntity;

    private String observacoes;

}
