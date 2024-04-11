package com.apajac.acolhimento.repositories;

import com.apajac.acolhimento.domain.entities.ContatoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContatoRepository extends JpaRepository<ContatoEntity, Long> {
}
