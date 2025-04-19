package com.apajac.acolhimento.controllers.relatorioController;

import com.apajac.acolhimento.services.interfaces.AssistidoMediaIdadeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/relatorio")
@Tag(name = "Relatórios", description = "Endpoints para geração de relatórios")
public class MediaIdadeController {

    private final AssistidoMediaIdadeService assistidoMediaIdadeService;

    @GetMapping("/mediaidade")
    public ResponseEntity<Map<String, List<String>>> getMediaIdade() {
        try {
            return ResponseEntity.ok(assistidoMediaIdadeService.calcularMediaGeralIdade());
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode(), e.getMessage());
        }
    }

    @GetMapping("/mediaidade/faixaetaria")
    public ResponseEntity<Map<String, List<String>>> getMediaPorFaixaEtaria() {
        try {
            return ResponseEntity.ok(assistidoMediaIdadeService.calcularMediaPorFaixaEtaria());
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode(), e.getMessage());
        }
    }
}
