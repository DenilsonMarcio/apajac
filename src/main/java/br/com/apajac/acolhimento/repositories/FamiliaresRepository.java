package br.com.apajac.acolhimento.repositories;

import br.com.apajac.acolhimento.domain.entities.FamiliaresEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FamiliaresRepository extends JpaRepository<FamiliaresEntity, Integer> {
}
