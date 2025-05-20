package com.apajac.acolhimento.repositories;

import com.apajac.acolhimento.domain.entities.MChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MChatRepository extends JpaRepository<MChatEntity, Long> {
    List<MChatEntity> findByAssistidoId(Long id);
}
