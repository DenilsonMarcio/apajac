package com.apajac.acolhimento.repositories;

import com.apajac.acolhimento.domain.entities.UsuarioEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
    Optional<UsuarioEntity> findByLogin(String login);
    Optional<UsuarioEntity> getUsuarioByLogin(String login);

    Page<UsuarioEntity> findAllByNomeContainingIgnoreCase(String nome, Pageable pageable);
}
