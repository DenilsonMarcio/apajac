package br.com.apajac.acolhimento.domain.entities;

import br.com.apajac.acolhimento.domain.Enum.Familiar;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Data

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_familiares")
public class FamiliaresEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String nome;

    @ElementCollection
    private List<String> telefone;

    private String ocupacao;
    private String localTrabalho;
    private Double salario;
    private String vinculoEmpregaticio;

    @Enumerated(EnumType.STRING)
    private Familiar tipoParentesco;

    @ManyToOne
    @JoinColumn(name = "acolhido_id", referencedColumnName = "id")
    @Setter
    private AcolhidoEntity acolhido;

}
