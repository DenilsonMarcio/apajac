package com.apajac.acolhimento.controllers.relatorioController;

import com.apajac.acolhimento.services.interfaces.AssistidosComSemPaiService;
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
public class AssistidosComSemPaiController {

    private final AssistidosComSemPaiService assistidosComSemPaiService;

    @GetMapping("/statpai")
    public ResponseEntity<Map<String, List<String>>> getAssistidosComSemPai() {
        try {
            return ResponseEntity.ok(assistidosComSemPaiService.totalAssistidosComSemPai());
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode(), e.getMessage());
        }
    }
}
