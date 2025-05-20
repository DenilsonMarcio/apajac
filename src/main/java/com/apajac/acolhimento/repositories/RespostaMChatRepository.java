package com.apajac.acolhimento.repositories;

import com.apajac.acolhimento.domain.entities.RespostaMChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RespostaMChatRepository extends JpaRepository<RespostaMChatEntity, Long> {
}
