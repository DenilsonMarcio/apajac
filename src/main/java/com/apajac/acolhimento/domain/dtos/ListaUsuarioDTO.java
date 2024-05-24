package com.apajac.acolhimento.domain.dtos;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ListaUsuarioDTO {
    private Boolean isLastPage;
    private List<UsuarioSemSenhaDTO> usuarios;
}
