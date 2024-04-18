package br.com.apajac.acolhimento.domain.entities;

import br.com.apajac.acolhimento.domain.dtos.FamiliaresDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_contrato_acolhido")
public class ContratoAcolhidoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "acolhido_id", referencedColumnName = "id")
    private AcolhidoEntity acolhido;

    @OneToMany
    @JoinColumn(name = "familiar_id", referencedColumnName = "id")
    private List<FamiliaresEntity> familiares;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "responsavel_id", referencedColumnName = "id")
    private AcolhidoEntity responsavel;

    @OneToMany
    @JoinColumn(name = "composicao_familiar_id", referencedColumnName = "id")
    private List<ComposicaoFamiliarEntity> composicaoFamiliar;

}
