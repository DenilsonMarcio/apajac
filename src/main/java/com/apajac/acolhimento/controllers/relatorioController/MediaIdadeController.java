package com.apajac.acolhimento.controllers.relatorioController;

import com.apajac.acolhimento.services.interfaces.AssistidoMediaIdadeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            Map<String, List<String>> resultado = assistidoMediaIdadeService.calcularMediaGeralIdade();
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
