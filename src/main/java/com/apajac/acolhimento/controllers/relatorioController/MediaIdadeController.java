package com.apajac.acolhimento.controllers.relatorioController;

import com.apajac.acolhimento.services.interfaces.AssistidoMediaIdadeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.hibernate.query.sqm.tree.SqmNode.log;

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

    @GetMapping("/mediaidade/faixaetaria")
    public ResponseEntity<Map<String, List<String>>> getMediaPorFaixaEtaria() {
        try {
            Map<String, List<String>> resultado = assistidoMediaIdadeService.calcularMediaPorFaixaEtaria();

            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            log.error("Erro ao calcular média por faixa etária", e);
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", List.of("Ocorreu um erro ao processar a requisição")));
        }
    }
}
