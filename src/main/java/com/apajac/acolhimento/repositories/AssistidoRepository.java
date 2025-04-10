package com.apajac.acolhimento.repositories;

import com.apajac.acolhimento.domain.entities.AssistidoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AssistidoRepository extends JpaRepository<AssistidoEntity, Long> {
    Page<AssistidoEntity> findAllByNomeContainingIgnoreCase(String nome, Pageable pageable);

    @Query("SELECT a.dataNascimento FROM AssistidoEntity a WHERE a.dataNascimento IS NOT NULL")
    List<LocalDate> findAllBirthDates();

}
