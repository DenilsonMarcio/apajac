package com.apajac.acolhimento.repositories;

import com.apajac.acolhimento.domain.entities.AcolhidoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AcolhidoRepository extends JpaRepository<AcolhidoEntity, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM acolhido WHERE nome LIKE %:nome%")
    Page<AcolhidoEntity> findAllByNome(String nome, Pageable pageable);
}
