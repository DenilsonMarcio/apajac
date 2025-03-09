package com.apajac.acolhimento.controllers.relatorioController;

import com.apajac.acolhimento.domain.dtos.relatorio.AtivosEInativosDTO;
import com.apajac.acolhimento.services.AtivosEInativosServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/relatorio")
public class TotalAtivosEInativosController {

    private final AtivosEInativosServiceImpl ativosEInativosService;

    @GetMapping("/ativos_inativos")
    public ResponseEntity<AtivosEInativosDTO> totalAtivosEInativos(){
        try {
            AtivosEInativosDTO dto = ativosEInativosService.totalAtivosEInativos();
            return ResponseEntity.ok().body(dto);
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode(), e.getMessage());
        }
    }
}
