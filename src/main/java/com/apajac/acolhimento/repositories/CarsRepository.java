package com.apajac.acolhimento.repositories;

import com.apajac.acolhimento.domain.entities.CarsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarsRepository extends JpaRepository<CarsEntity, Long> {
    List<CarsEntity> findByAssistidoId(Long id);
}
