package com.apajac.acolhimento.domain.dtos;

import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Validated
public class UsuarioDTO {
    private Long id;
    private String nome;
    private String login;
    private String password;
    private Set<String> roles;
    private Long idResponsavelPeloCadastro;
}
