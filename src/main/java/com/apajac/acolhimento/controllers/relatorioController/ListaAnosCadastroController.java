package com.apajac.acolhimento.controllers.relatorioController;

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
public class ListaAnosCadastroController {

    private final ConsultarAssistidoService assistidoService;

    @GetMapping("/anos_cadastros")
    public ResponseEntity<List<Integer>> getAnosCadastros(){
        try {
            List<Integer> anosCadastrados = assistidoService.getAnosCadastros();
            return ResponseEntity.ok().body(anosCadastrados);
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode(), e.getMessage());
        }
    }
}
