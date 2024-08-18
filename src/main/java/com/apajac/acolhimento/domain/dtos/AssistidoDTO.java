package com.apajac.acolhimento.domain.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class AssistidoDTO {

    private Long id;

    @NotBlank(message = "O campo 'nome' em Assistido, é obrigatorio")
    private String nome;
    @NotNull(message = "O campo 'data de nascimento' em Assistido, é obrigatorio")
    @Past(message = "O campo 'data de nascimento' em Assistido, deve ser no passado")
    private LocalDate dataNascimento;
    @NotBlank(message = "O campo 'escolaridade' em Assistido, é obrigatorio")
    private String escolaridade;
    private String escola;
    private String telEscola;
    private boolean cadastroInstituicao;
    //feita a validação manualmente, devido a condição 'cadastroInstituicao' precisar ser true;
    private String instituicao;
    private String encaminhadoPara;
    private String quemIndicouApajac;
    @NotBlank(message = "O campo 'informacoes fornecidas por' em Assistido, é obrigatorio")
    private String informacoesFornecidasPor;

    private Long idResponsavelPeloCadastro;
    private LocalDate cadastradoEm;

    @NotNull(message = "O endereço é obrigatório")
    @Valid
    private EnderecoDTO endereco;
    private String observacoes;
    @Valid
    private List<FamiliarDTO> familiares;
    @Valid
    private List<ComposicaoFamiliarDTO> composicaoFamiliar;
    @Valid
    private ResponsavelDTO responsavel;
    private boolean statusAssistido;
}
