package com.apajac.acolhimento.domain.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "mchat")
public class MChatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Boolean pea;

    private LocalDate realizadoEm;

    @ManyToOne
    @JoinColumn(name = "assistido_id")
    private AssistidoEntity assistido;

    @OneToMany(mappedBy = "mchat", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<RespostaMChatEntity> respostas;
}
