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

    private Long id;
    @NotNull(message = "Nome não pode ser nulo")
    @NotBlank(message = "Nome não pode ser nulo ou vazio")
    private String nome;
    @NotNull(message = "Documento não pode ser nulo")
    @NotBlank(message = "Documento não pode ser nulo ou vazio")
    private String documento;
    @NotNull(message = "Valor não pode ser nulo")
    @NotBlank(message = "Valor não pode ser nulo ou vazio")
    private BigDecimal valor;
    @Enumerated(EnumType.STRING)
    @JsonProperty("tipo_doador")
    @NotNull(message = "Tipo de Doador não pode ser nulo")
    @NotBlank(message = "Tipo de Doador não pode ser nulo ou vazio")
    private TipoDoador tipoDoador;

    @JsonProperty("como_conheceu")
    private String comoConheceu;
    private Long idResponsavelPeloCadastro;

}

