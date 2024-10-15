package com.apajac.acolhimento.domain.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotBlank(message = "O campo 'nome' é obrigatorio")
    private String nome;
    @NotBlank(message = "O campo 'login' é obrigatorio")
    private String login;
    @NotBlank(message = "O campo 'password' é obrigatorio")
    private String password;
    @NotNull(message = "O campo role é obrigatório")
    @Valid
    private Set<String> roles;
    private Long idResponsavelPeloCadastro;
}
