package com.apajac.acolhimento.repositories;

import com.apajac.acolhimento.domain.entities.DoadorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoadorRepository extends JpaRepository<DoadorEntity, Long> {
}
