package com.apajac.acolhimento.services.interfaces;

import com.apajac.acolhimento.domain.entities.AuditoriaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuditoriaService {

    void inserirDadosDeAuditoria(Long idUsuario, String tipo, String servico, String body);

    Page<AuditoriaEntity> listarHistoricoDeAlteracoes(Pageable pageable);
}
