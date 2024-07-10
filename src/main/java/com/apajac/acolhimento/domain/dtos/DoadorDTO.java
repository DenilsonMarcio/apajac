package com.apajac.acolhimento.domain.dtos;

import com.apajac.acolhimento.domain.enums.TipoDoador;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class DoadorDTO {

    private String nome;
    private String documento;
    private BigDecimal valor;
    private TipoDoador tipoDoador;

    private String comoConheceu;
    private Long idResponsavelPeloCadastro;

}

