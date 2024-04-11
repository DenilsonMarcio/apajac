package com.apajac.acolhimento.repositories;

import com.apajac.acolhimento.domain.entities.ResponsavelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponsavelRepository extends JpaRepository<ResponsavelEntity, Long> {
}
