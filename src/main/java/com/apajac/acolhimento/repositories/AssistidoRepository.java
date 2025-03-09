package com.apajac.acolhimento.repositories;

import com.apajac.acolhimento.domain.entities.AssistidoEntity;

import jakarta.persistence.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssistidoRepository extends JpaRepository<AssistidoEntity, Long> {
    Page<AssistidoEntity> findAllByNomeContainingIgnoreCase(String nome, Pageable pageable);

    @Query(nativeQuery = true, value = """
            SELECT
                a.sexo,
                COUNT(*) AS total
            FROM assistido a
            GROUP BY a.sexo
            """)
    List<Tuple> totalAssistidoPorSexo();

    @Query(nativeQuery = true, value = """
            SELECT
                a.nome,
                a.data_nascimento,
                EXTRACT(YEAR FROM AGE(a.data_nascimento)) AS idade
            FROM assistido a
            WHERE EXTRACT(MONTH FROM a.data_nascimento) = EXTRACT(MONTH FROM CURRENT_DATE)
            ORDER by a.data_nascimento
            """)
    List<Tuple> getAniversariantesDoMes();

    @Query(nativeQuery = true, value = """
            SELECT
                a.status_assistido,
                COUNT(*) AS total
            FROM assistido a
            GROUP BY a.status_assistido
            """)
    List<Tuple> totalAtivosEInativos();

    @Query(nativeQuery = true, value = """
            SELECT
                EXTRACT(YEAR FROM AGE(a.data_nascimento)) AS idade,
                COUNT(*) AS total
            FROM assistido a
            WHERE a.status_assistido = TRUE
            GROUP BY idade
            ORDER BY idade ASC
            """)
    List<Tuple> totalAssistidosPorIdade();
}
