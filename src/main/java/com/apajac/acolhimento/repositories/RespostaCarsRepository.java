package com.apajac.acolhimento.repositories;

import com.apajac.acolhimento.domain.entities.RespostaCarsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RespostaCarsRepository extends JpaRepository<RespostaCarsEntity, Long> {
}
