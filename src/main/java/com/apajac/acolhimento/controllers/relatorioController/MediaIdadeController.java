package com.apajac.acolhimento.controllers.relatorioController;

import com.apajac.acolhimento.services.interfaces.AssistidoMediaIdadeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/relatorio")
@Tag(name = "Relatórios", description = "Endpoints para geração de relatórios")
public class MediaIdadeController {

    private final AssistidoMediaIdadeService assistidoMediaIdadeService;

    @GetMapping("/mediaidade")
    public ResponseEntity<Map<String, Integer>> getMediaIdade() {
        try {
            int media = assistidoMediaIdadeService.calcularMediaIdade();
            return ResponseEntity.ok(Map.of("MediaId", media));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
