package br.com.apajac.acolhimento.repositories;

import br.com.apajac.acolhimento.domain.entities.ResponsavelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResponsavelRepository extends JpaRepository<ResponsavelEntity, Integer> {
}
