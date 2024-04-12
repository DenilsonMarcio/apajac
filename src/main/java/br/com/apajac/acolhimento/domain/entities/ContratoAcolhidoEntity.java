package br.com.apajac.acolhimento.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "tb_contrato_acolhido")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ContratoAcolhidoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "acolhido_id", referencedColumnName = "id")
    private AcolhidoEntity acolhido;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "mae_id", referencedColumnName = "id")
    private MaeEntity mae;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pai_id", referencedColumnName = "id")
    private PaiEntity pai;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "responsavel_id", referencedColumnName = "id")
    private ResponsavelEntity responsavel;

}
