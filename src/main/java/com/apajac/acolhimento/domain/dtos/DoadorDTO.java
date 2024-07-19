package com.apajac.acolhimento.domain.dtos;

import com.apajac.acolhimento.domain.enums.TipoDoador;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class DoadorDTO {

    private Long id;
    private String nome;
    private String documento;
    private BigDecimal valor;
    private TipoDoador tipoDoador;

    private String comoConheceu;
    private Long idResponsavelPeloCadastro;

}
