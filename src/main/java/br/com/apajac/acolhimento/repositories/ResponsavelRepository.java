package br.com.apajac.acolhimento.repositories;

import br.com.apajac.acolhimento.domain.entities.ResponsavelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResponsavelRepository extends JpaRepository<ResponsavelEntity, Integer> {

    Optional<ResponsavelEntity> findByNome(String nome);


}
