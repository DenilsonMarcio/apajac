package com.apajac.acolhimento.controllers.relatoriosController;

import com.apajac.acolhimento.services.interfaces.RelatoriosService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/relatorio")
public class MediaIdadeController {

    private final RelatoriosService relatoriosService;

    @GetMapping("/mediaidade")
    public ResponseEntity<Map<String, Integer>> getMediaIdade() {
        try {
            int media = relatoriosService.calcularMediaIdade();
            return ResponseEntity.ok(Map.of("MediaId", media));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
