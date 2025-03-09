package com.apajac.acolhimento.controllers.relatorioController;

import com.apajac.acolhimento.domain.dtos.relatorio.PorSexoDTO;
import com.apajac.acolhimento.services.interfaces.AssistidoPorSexoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/relatorio")
public class TotalAssistidoPorSexoControler {

    private final AssistidoPorSexoService assistidoPorSexoService;

    @GetMapping("/total_por_sexo")
    public ResponseEntity<PorSexoDTO> totalAssistidoPorSexo(){
        try {
            PorSexoDTO dto = assistidoPorSexoService.totalAssistidoPorSexo();
            return ResponseEntity.ok().body(dto);
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode(), e.getMessage());
        }
    }
}
