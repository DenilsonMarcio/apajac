package com.apajac.acolhimento.controllers.relatorioController;

import com.apajac.acolhimento.domain.dtos.relatorio.AniversarianteDoMesDTO;
import com.apajac.acolhimento.domain.dtos.relatorio.PorSexoDTO;
import com.apajac.acolhimento.services.AniversarianteDoMesServiceImpl;
import com.apajac.acolhimento.services.interfaces.AniversarianteDoMesService;
import com.apajac.acolhimento.services.interfaces.AssistidoPorSexoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/relatorio")
public class AniversarianteDoMesController {

    private final AniversarianteDoMesServiceImpl aniversarianteDoMesService;

    @GetMapping("/aniversariantes")
    public ResponseEntity<List<AniversarianteDoMesDTO>> aniversariantesDoMes(){
        try {
            List<AniversarianteDoMesDTO> aniversariantes = aniversarianteDoMesService.aniversariantesDoMes();
            return ResponseEntity.ok().body(aniversariantes);
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode(), e.getMessage());
        }
    }
}
