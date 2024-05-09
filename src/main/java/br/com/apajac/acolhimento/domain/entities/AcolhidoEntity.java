package br.com.apajac.acolhimento.domain.entities;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String statusAcolhido;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    private EnderecoEntity endereco;
    private String observacoes;

    @OneToMany(mappedBy = "acolhido")
    private List<FamiliaresEntity> familiar;

    @ManyToOne
    @Setter
    @JoinColumn(name = "responsavel_id", referencedColumnName = "id")
    private ResponsavelEntity responsavel;

    @OneToMany(mappedBy = "acolhido")
    private List<ComposicaoFamiliarEntity> composicaoFamiliar;

    private LocalDate criadoEm = LocalDate.now();

}