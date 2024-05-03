package br.com.apajac.acolhimento.repositories;

import br.com.apajac.acolhimento.domain.entities.ComposicaoFamiliarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComposicaoFamiliarRepository extends JpaRepository<ComposicaoFamiliarEntity, String> {
}
