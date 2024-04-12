package br.com.apajac.acolhimento.controllers;

import br.com.apajac.acolhimento.domain.dtos.AcolhidoDTO;
import br.com.apajac.acolhimento.domain.dtos.ComposicaoFamiliarDTO;
import br.com.apajac.acolhimento.services.interfaces.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/acolhidos")
public class AcolhidoController {

    private final AcolhidoService acolhidoService;


    public AcolhidoController(AcolhidoService acolhidoService) {
        this.acolhidoService = acolhidoService;
    }

    @PostMapping
    public ResponseEntity<Void> cadastrarContrato(@RequestBody AcolhidoDTO acolhidoDTO) {
        acolhidoService.cadastrarAcolhido(acolhidoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
