package com.apajac.acolhimento.repositories;

import com.apajac.acolhimento.domain.entities.ContatoEntity;
import com.apajac.acolhimento.domain.entities.FamiliarEntity;
import com.apajac.acolhimento.domain.entities.ResponsavelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContatoRepository extends JpaRepository<ContatoEntity, Long> {
    List<ContatoEntity> findByResponsavel(ResponsavelEntity responsavel);

    List<ContatoEntity> findByFamiliar(FamiliarEntity familiar);
}
