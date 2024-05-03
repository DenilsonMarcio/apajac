package com.apajac.acolhimento.domain.entities;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Setter
@Getter
public class Endereco {
    private String cep;
    private String endereco;
    private String numero;
    private String bairro;
    private String cidade;
    private String UF;
    private String complemento;
}