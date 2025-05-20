package com.apajac.acolhimento.repositories;

import com.apajac.acolhimento.domain.entities.AssistidoEntity;

import jakarta.persistence.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
                a.id,
                a.nome,
                a.data_nascimento,
                EXTRACT(YEAR FROM AGE(a.data_nascimento)) AS idade
            FROM assistido a
            WHERE EXTRACT(MONTH FROM a.data_nascimento) = :mes
            ORDER BY EXTRACT(DAY FROM a.data_nascimento);
            """)
    List<Tuple> getAniversariantesDoMes(Integer mes);

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

    @Query(nativeQuery = true, value = """
            SELECT
                EXTRACT(MONTH FROM a.cadastrado_em) AS mes,
                EXTRACT(YEAR FROM a.cadastrado_em) AS ano,
                COUNT(*) AS quantidade_cadastrados
            FROM
                assistido a
            WHERE
                EXTRACT(YEAR FROM a.cadastrado_em) = :codAno
            GROUP BY
                ano, mes
            ORDER BY
                ano, mes;
            """)
    List<Tuple> getCadastradosMensal(Integer codAno);

    @Query(nativeQuery = true, value = """
             SELECT
                CASE
                    WHEN EXTRACT(YEAR FROM AGE(CURRENT_DATE, a.data_nascimento)) BETWEEN 0 AND 4 THEN '0-4 anos'
                    WHEN EXTRACT(YEAR FROM AGE(CURRENT_DATE, a.data_nascimento)) BETWEEN 5 AND 9 THEN '5-9 anos'
                    WHEN EXTRACT(YEAR FROM AGE(CURRENT_DATE, a.data_nascimento)) BETWEEN 10 AND 14 THEN '10-14 anos'
                    WHEN EXTRACT(YEAR FROM AGE(CURRENT_DATE, a.data_nascimento)) BETWEEN 15 AND 19 THEN '15-19 anos'
                    WHEN EXTRACT(YEAR FROM AGE(CURRENT_DATE, a.data_nascimento)) BETWEEN 20 AND 24 THEN '20-24 anos'
                    WHEN EXTRACT(YEAR FROM AGE(CURRENT_DATE, a.data_nascimento)) BETWEEN 25 AND 29 THEN '25-29 anos'
                    WHEN EXTRACT(YEAR FROM AGE(CURRENT_DATE, a.data_nascimento)) BETWEEN 30 AND 34 THEN '30-34 anos'
                    WHEN EXTRACT(YEAR FROM AGE(CURRENT_DATE, a.data_nascimento)) BETWEEN 35 AND 39 THEN '35-39 anos'
                    ELSE '40+'
                END AS faixa_etaria,
                COUNT(*) AS quantidade
            FROM assistido a
            WHERE a.data_nascimento IS NOT NULL
            GROUP BY faixa_etaria
            ORDER BY faixa_etaria ASC;
            """)
    List<Tuple> totalAssistidoPorFaixaEtaria();

    @Query(nativeQuery = true, value = """
            SELECT DISTINCT
                EXTRACT(YEAR FROM a.cadastrado_em) AS ano
            FROM
                assistido a
            WHERE
                a.cadastrado_em IS NOT NULL
            ORDER BY
                ano;
            """)
    List<Integer> getAnosCadastros();

    @Query(nativeQuery = true, value = """
        SELECT
            a.bairro,
            COUNT(*) AS total_assistidos,
            AVG(EXTRACT(YEAR FROM AGE(current_date, a.data_nascimento))) AS media_idade
        FROM
            assistido a
        WHERE
            (:bairro IS NULL OR a.bairro ILIKE CONCAT('%', :bairro, '%'))
          AND a.data_nascimento IS NOT NULL
        GROUP BY
            a.bairro
        ORDER BY
            total_assistidos DESC
        """)
    List<Tuple> totalAssistidosPorBairro(@Param("bairro") String bairro);
}
