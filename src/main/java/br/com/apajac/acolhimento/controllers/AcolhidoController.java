package br.com.apajac.acolhimento.controllers;

import br.com.apajac.acolhimento.domain.dtos.ContratoAcolhidoDTO;
import br.com.apajac.acolhimento.services.interfaces.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/acolhidos")
public class AcolhidoController {

    private final ContratoAcolhidoService contratoAcolhidoService;


    public AcolhidoController(ContratoAcolhidoService contratoAcolhidoService) {
        this.contratoAcolhidoService = contratoAcolhidoService;
    }

    @PostMapping
    public ResponseEntity<Void> cadastrarContrato(@RequestBody ContratoAcolhidoDTO contratoAcolhidoDTO) {
        contratoAcolhidoService.cadastrarContrato(contratoAcolhidoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
