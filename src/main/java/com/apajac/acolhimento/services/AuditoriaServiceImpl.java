package com.apajac.acolhimento.services;

import com.apajac.acolhimento.domain.entities.AuditoriaEntity;
import com.apajac.acolhimento.exceptions.BusinessException;
import com.apajac.acolhimento.repositories.AuditoriaRepository;
import com.apajac.acolhimento.services.interfaces.AuditoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class AuditoriaServiceImpl implements AuditoriaService {

    private final AuditoriaRepository repository;
    @Override
    public void inserirDadosDeAuditoria(Long idUsuario, String tipo, String servico, String body) {
        try {
            AuditoriaEntity auditoria = new AuditoriaEntity();
            auditoria.setIdUsuario(idUsuario);
            auditoria.setTipo(tipo);
            auditoria.setServico(servico);
            auditoria.setBody(body);
            auditoria.setData(LocalDateTime.now());

            repository.save(auditoria);
        } catch (Exception exception) {
            throw new BusinessException("Não foi possivel inserir dados de auditoria.", exception);
        }

    }

    @Override
    public Page<AuditoriaEntity> listarHistoricoDeAlteracoes(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
