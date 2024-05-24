package com.apajac.acolhimento.controllers.auditoriaController;

import com.apajac.acolhimento.domain.entities.AuditoriaEntity;
import com.apajac.acolhimento.services.interfaces.AuditoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/lista_auditoria")
public class ListarAlteracoesController {

    private final AuditoriaService auditoriaService;

    @GetMapping
    ResponseEntity<Page<AuditoriaEntity>> listarHistoricoDeAlteracoes(Pageable pageable){
        try {
            Page<AuditoriaEntity> entities = auditoriaService.listarHistoricoDeAlteracoes(pageable);
            return ResponseEntity.status(HttpStatus.OK).body(entities);
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode(), e.getMessage());
        }
    }

}
