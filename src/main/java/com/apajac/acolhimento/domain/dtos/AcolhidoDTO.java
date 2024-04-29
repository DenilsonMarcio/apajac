package com.apajac.acolhimento.domain.dtos;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class AcolhidoDTO {
    private Long id;
    private String nome;
    private LocalDate dataNascimento;
    private String escolaridade;
    private String escola;
    private String telEscola;
    private boolean cadastroInstituicao;
    private String instituicao;
    private String encaminhadoPara;
    private String quemIndicouApajac;
    private String informacoesFornecidasPor;
    private EnderecoDTO endereco;
    private String observacoes;
    private List<FamiliarDTO> familiares;
    private List<ComposicaoFamiliarDTO> composicaoFamiliar;
    private ResponsavelDTO responsavel;
    private boolean statusAcolhido;
}
