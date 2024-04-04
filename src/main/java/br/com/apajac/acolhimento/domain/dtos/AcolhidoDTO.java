package br.com.apajac.acolhimento.domain.dtos;

import jakarta.annotation.Nullable;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class AcolhidoDTO {

    private String nome;
    private LocalDate data_nascimento;
    private String escolaridade;
    private String escola;
    private String tel_escola;
    private boolean cadastro_instituicao;
    private String instituicao;
    private String encaminhado_para;
    private String quem_indicou_apajac;
    private String informacoes_fornecidas_por;
    private EnderecoDTO endereco;
    private String observacoes;

    @Override
    public String toString() {
        return  "nome='" + nome + '\'' +
                ", data_nascimento=" + data_nascimento +
                ", escolaridade='" + escolaridade + '\'' +
                ", escola='" + escola + '\'' +
                ", tel_escola='" + tel_escola + '\'' +
                ", cadastro_instituicao=" + cadastro_instituicao +
                ", instituicao='" + instituicao + '\'' +
                ", encaminhado_para='" + encaminhado_para + '\'' +
                ", quem_indicou_apajac='" + quem_indicou_apajac + '\'' +
                ", informacoes_fornecidas_por='" + informacoes_fornecidas_por + '\'' +
                ", endereco=" + endereco +
                ", observacoes='" + observacoes + '\'' +
                '}';
    }

}
