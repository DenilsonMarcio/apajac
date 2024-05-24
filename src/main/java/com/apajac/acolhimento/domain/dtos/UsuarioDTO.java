package com.apajac.acolhimento.domain.dtos;

import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

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
    private List<String> roles;
    private Long idResponsavelPeloCadastro;
}
