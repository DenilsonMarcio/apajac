package com.apajac.acolhimento.repositories;

import com.apajac.acolhimento.domain.entities.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {
    Optional<UsuarioEntity> getUsuarioByLoginAndPassword(String login, String password);
}
