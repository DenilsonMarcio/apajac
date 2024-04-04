package br.com.apajac.acolhimento.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "responsavel_db")
public class ResponsavelEntity {

    @Id
    private String nome;
    private String tel;

}
