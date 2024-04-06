package br.com.apajac.acolhimento.repositories;

import br.com.apajac.acolhimento.domain.entities.FamiliarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FamiliarRepository extends JpaRepository<FamiliarEntity, Long> {
}
