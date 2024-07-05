package com.apajac.acolhimento.repositories;

import com.apajac.acolhimento.domain.entities.DoadorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoadorRepository extends JpaRepository<DoadorEntity, Long> {
}
