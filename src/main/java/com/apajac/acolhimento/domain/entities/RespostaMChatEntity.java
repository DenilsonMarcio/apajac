package com.apajac.acolhimento.domain.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "respostas_mchat")
public class RespostaMChatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer pergunta;

    private Boolean resposta;

    @ManyToOne
    @JoinColumn(name = "mchat_id")
    private MChatEntity mchat;
}
