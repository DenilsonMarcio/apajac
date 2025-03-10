package com.apajac.acolhimento.controllers.relatorioController;

import com.apajac.acolhimento.domain.dtos.relatorio.AniversarianteDoMesDTO;
import com.apajac.acolhimento.services.AniversarianteDoMesServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/relatorio")
public class AniversarianteDoMesController {

    private final AniversarianteDoMesServiceImpl aniversarianteDoMesService;

    @GetMapping("/aniversariantes/{mes}")
    public ResponseEntity<List<AniversarianteDoMesDTO>> aniversariantesDoMes(@RequestParam Integer mes){
        try {
            List<AniversarianteDoMesDTO> aniversariantes = aniversarianteDoMesService.aniversariantesDoMes(mes);
            return ResponseEntity.ok().body(aniversariantes);
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode(), e.getMessage());
        }
    }
}
