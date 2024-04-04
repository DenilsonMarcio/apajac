package br.com.apajac.acolhimento.domain.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class AcolhidoDTO {

    private String nome;
    @JsonProperty("data_nascimento")
    private LocalDate dataNascimento;
    private String escolaridade;
    private String escola;
    @JsonProperty("tel_escola")
    private String telEscola;
    @JsonProperty("cadastro_instituicao")
    private boolean cadastroInstituicao;
    private String instituicao;
    @JsonProperty("encaminhado_para")
    private String encaminhadoPara;
    @JsonProperty("quem_indicou_apajac")
    private String quemIndicouApajac;
    @JsonProperty("informacoes_fornecidas_por")
    private String informacoesFornecidasPor;
    private EnderecoDTO endereco;
    private String observacoes;

}
