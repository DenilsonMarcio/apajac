package com.apajac.acolhimento.controllers.relatorioController;

import com.apajac.acolhimento.domain.dtos.relatorio.FaixaEtariaDTO;
import com.apajac.acolhimento.services.interfaces.AssistidoPorFaixaEtariaService;
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
public class TotalAssistidoPorFaixaEtariaController {

    private final AssistidoPorFaixaEtariaService assistidoPorFaixaEtariaService;

    @GetMapping("/faixa_etaria")
    public ResponseEntity<FaixaEtariaDTO> totalAssistidoPorFaixaEtaria(){
        try {
            FaixaEtariaDTO dto = assistidoPorFaixaEtariaService.totalAssistidoPorFaixaEtaria();
            return ResponseEntity.ok().body(dto);
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode(), e.getMessage());
        }
    }
}
