package br.com.apajac.acolhimento.domain.entities;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_acolhido")
public class AcolhidoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_cep", referencedColumnName = "cep")
    private EnderecoEntity enderecoEntity;

    private String observacoes;

}
