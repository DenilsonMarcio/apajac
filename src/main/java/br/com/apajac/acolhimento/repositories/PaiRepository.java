package br.com.apajac.acolhimento.repositories;

import br.com.apajac.acolhimento.domain.entities.PaiEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaiRepository extends JpaRepository<PaiEntity, Integer> {
}
