package com.apajac.acolhimento.controllers.relatorioController;

import com.apajac.acolhimento.domain.dtos.relatorio.AssistidoPorBairroDTO;
import com.apajac.acolhimento.services.interfaces.ConsultarAssistidoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/relatorio")
@Tag(name = "Relatórios", description = "Endpoints para geração de relatórios")
public class TotalAssistidoPorBairroController {

    private final ConsultarAssistidoService assistidoService;

    @GetMapping("/total_assistidos_por_bairro/{bairro}")
    public ResponseEntity<List<AssistidoPorBairroDTO>> totalAssistidoPorBairro(@PathVariable("bairro") String bairro){
        try {
            List<AssistidoPorBairroDTO> assistidoPorBairro = assistidoService.totalAssistidoPorBairro(bairro);
            return ResponseEntity.ok().body(assistidoPorBairro);
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode(), e.getMessage());
        }
    }

}
