package com.apajac.acolhimento.controllers.relatorioController;

import com.apajac.acolhimento.domain.dtos.relatorio.PorIdadeDTO;
import com.apajac.acolhimento.services.AssistidosPorIdadeServiceImpl;
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
public class TotalAssistidosPorIdadeController {

    private final AssistidosPorIdadeServiceImpl assistidosPorIdadeService;

    @GetMapping("/total_por_idade")
    public ResponseEntity<PorIdadeDTO> totalAssistidosPorIdade(){
        try {
            PorIdadeDTO dto = assistidosPorIdadeService.totalAssistidosPorIdade();
            return ResponseEntity.ok().body(dto);
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode(), e.getMessage());
        }
    }
}
