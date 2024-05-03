package com.apajac.acolhimento.repositories;

import com.apajac.acolhimento.domain.entities.AcolhidoEntity;
import com.apajac.acolhimento.domain.entities.FamiliarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FamiliarRepository extends JpaRepository<FamiliarEntity, Long> {
    List<FamiliarEntity> findByAcolhido(AcolhidoEntity acolhido);
}
