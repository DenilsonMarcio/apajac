package com.apajac.acolhimento.controllers.aplicacaoController;

import com.apajac.acolhimento.gateway.SquareCloudIntegration;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/info")
@Tag(name = "Info", description = "Endpoint para gerenciamento da aplicação")
public class DataExpiracaoController {
    private final SquareCloudIntegration integration;
    @GetMapping
    public ResponseEntity<Map<String, Object>> getExpiracao() {
        Map<String, Object> response = integration.getExpiracaoInfo();
        return ResponseEntity.ok(response);
    }
}
