package com.apajac.acolhimento.repositories;

import com.apajac.acolhimento.domain.entities.AssistidoEntity;
import com.apajac.acolhimento.domain.entities.FamiliarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface FamiliarRepository extends JpaRepository<FamiliarEntity, Long> {
    List<FamiliarEntity> findByAssistido(AssistidoEntity assistido);

    @Query("SELECT f.salario FROM FamiliarEntity f")
    List<BigDecimal> findAllRendas();

    @Query("SELECT f.assistido.id, f.assistido.nome, f.salario FROM FamiliarEntity f")
    List<Object[]> findAllAssistidoIdNomeAndRenda();

}
