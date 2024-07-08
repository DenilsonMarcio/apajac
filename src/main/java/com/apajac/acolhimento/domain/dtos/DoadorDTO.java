package com.apajac.acolhimento.domain.dtos;

import com.apajac.acolhimento.domain.enums.TipoDoador;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

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
    @Enumerated(EnumType.STRING)
    @JsonProperty("tipo_doador")
    private TipoDoador tipoDoador;

    @JsonProperty("como_conheceu")
    private String comoConheceu;
    private Long idResponsavelPeloCadastro;

}

