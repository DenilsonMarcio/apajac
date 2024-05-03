package br.com.apajac.acolhimento.controllers;

import br.com.apajac.acolhimento.domain.dtos.ContratoAcolhidoDTO;
import br.com.apajac.acolhimento.services.interfaces.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping("/acolhidos")
public class AcolhidoController {

    private final AcolhidoService acolhidoService;

    public AcolhidoController(AcolhidoService acolhidoService) {
        this.acolhidoService = acolhidoService;
    }

    @PostMapping
    public ResponseEntity<Void> cadastrarContrato(@org.jetbrains.annotations.NotNull @RequestBody ContratoAcolhidoDTO contratoAcolhidoDTO) {
        try {
            acolhidoService.cadastrarAcolhido(contratoAcolhidoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode(), e.getMessage());
        }
    }
}