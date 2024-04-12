package br.com.apajac.acolhimento.repositories;

import br.com.apajac.acolhimento.domain.entities.MaeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContratoAcolhidoRepository extends JpaRepository<MaeEntity, Integer> {
}
