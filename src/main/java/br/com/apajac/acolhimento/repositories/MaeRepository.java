package br.com.apajac.acolhimento.repositories;

import br.com.apajac.acolhimento.domain.entities.MaeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaeRepository extends JpaRepository<MaeEntity, String> {
}
