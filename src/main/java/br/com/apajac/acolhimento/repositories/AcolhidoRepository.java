package br.com.apajac.acolhimento.repositories;

import br.com.apajac.acolhimento.domain.entities.AcolhidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcolhidoRepository extends JpaRepository<AcolhidoEntity, Integer> {
}
