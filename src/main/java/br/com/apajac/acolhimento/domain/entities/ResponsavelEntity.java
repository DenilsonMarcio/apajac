package br.com.apajac.acolhimento.domain.entities;

import br.com.apajac.acolhimento.domain.Enum.Familiar;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_responsavel")
public class ResponsavelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String nome;
    @ElementCollection
    private List<String> telefone;
    @Enumerated(EnumType.STRING)
    private Familiar tipoParentesco;

}
