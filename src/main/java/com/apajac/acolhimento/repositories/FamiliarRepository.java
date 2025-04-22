package com.apajac.acolhimento.repositories;

import com.apajac.acolhimento.domain.entities.AssistidoEntity;
import com.apajac.acolhimento.domain.entities.FamiliarEntity;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FamiliarRepository extends JpaRepository<FamiliarEntity, Long> {
    List<FamiliarEntity> findByAssistido(AssistidoEntity assistido);

    @Query(nativeQuery = true, value = """
            SELECT
                true as tempai
            FROM Familiar f
            where f.tipo_parentesco = 'PAI' and f.assistido_id = :AID and f.nome is not null
            ORDER BY f.nome;
            """)
    List<Tuple> ListaPorPaiC(Long AID);
}
