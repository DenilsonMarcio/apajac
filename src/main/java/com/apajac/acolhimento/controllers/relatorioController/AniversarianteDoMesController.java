package com.apajac.acolhimento.controllers.relatorioController;

import com.apajac.acolhimento.domain.dtos.relatorio.AniversarianteDoMesDTO;
import com.apajac.acolhimento.services.AniversarianteDoMesServiceImpl;
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
public class AniversarianteDoMesController {

    private final AniversarianteDoMesServiceImpl aniversarianteDoMesService;

    @GetMapping("/aniversariantes/{codMes}")
    public ResponseEntity<List<AniversarianteDoMesDTO>> aniversariantesDoMes(@PathVariable("codMes") Integer codMes){
        try {
            List<AniversarianteDoMesDTO> aniversariantes = aniversarianteDoMesService.aniversariantesDoMes(codMes);
            return ResponseEntity.ok().body(aniversariantes);
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode(), e.getMessage());
        }
    }
}
