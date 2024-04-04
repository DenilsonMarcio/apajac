package br.com.apajac.acolhimento.repositories;

import br.com.apajac.acolhimento.domain.entities.ParenteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParenteRepository extends JpaRepository<ParenteEntity, Integer> {
}
