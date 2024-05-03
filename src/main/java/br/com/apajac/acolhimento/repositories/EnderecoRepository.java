package br.com.apajac.acolhimento.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.apajac.acolhimento.domain.entities.EnderecoEntity;
import org.springframework.stereotype.Repository;


@Repository
public interface EnderecoRepository extends JpaRepository<EnderecoEntity, Integer> {

}
