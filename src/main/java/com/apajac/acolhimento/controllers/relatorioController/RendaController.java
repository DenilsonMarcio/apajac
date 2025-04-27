package com.apajac.acolhimento.controllers.relatorioController;

import com.apajac.acolhimento.services.interfaces.FamiliarMediaRendaService;
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
public class RendaController {

    private final FamiliarMediaRendaService familiarMediaRendaService;

    @GetMapping("/mediarenda")
    public ResponseEntity<Map<String, List<String>>> calcularMediaGeralRenda() {
        try {
            Map<String, List<String>> resultado = familiarMediaRendaService.calcularMediaGeralRenda();
            return ResponseEntity.ok().body(resultado);
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode(), e.getMessage());
        }
    }

    @GetMapping("/mediarenda/porassistido")
    public ResponseEntity<Map<String, List<String>>> calcularMediaRendaPorAssistido() {
        try {
            Map<String, List<String>> resultado = familiarMediaRendaService.calcularMediaRendaPorAssistido();
            return ResponseEntity.ok().body(resultado);
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode(), e.getMessage());
        }
    }

    @GetMapping("/mediarenda/faixas")
    public ResponseEntity<Map<String, List<String>>> calcularFaixasDeRenda() {
        try {
            Map<String, List<String>> resultado = familiarMediaRendaService.calcularFaixasDeRenda();
            return ResponseEntity.ok().body(resultado);
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode(), e.getMessage());
        }
    }
}
