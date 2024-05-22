package com.apajac.acolhimento.repositories;

import com.apajac.acolhimento.domain.entities.AssistidoEntity;
import com.apajac.acolhimento.domain.entities.ComposicaoFamiliarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComposicaoFamiliarRepository extends JpaRepository<ComposicaoFamiliarEntity, Long> {
    List<ComposicaoFamiliarEntity> findByAssistido(AssistidoEntity assistido);
}
