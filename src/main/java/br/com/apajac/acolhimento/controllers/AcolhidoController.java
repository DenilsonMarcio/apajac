package br.com.apajac.acolhimento.controllers;

import br.com.apajac.acolhimento.domain.dtos.AcolhidoDTO;
import br.com.apajac.acolhimento.domain.dtos.ContratoAcolhidoDTO;
import br.com.apajac.acolhimento.domain.dtos.EnderecoDTO;
import br.com.apajac.acolhimento.domain.dtos.FamiliaresDTO;
import br.com.apajac.acolhimento.domain.entities.EnderecoEntity;
import br.com.apajac.acolhimento.services.FamiliaresServiceImpl;
import br.com.apajac.acolhimento.services.interfaces.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@RestController
@RequestMapping("/acolhidos")
public class AcolhidoController {

    private final ContratoAcolhidoService CAservice;

    public AcolhidoController(ContratoAcolhidoService CAservice) {
        this.CAservice = CAservice;
    }

    @PostMapping
    public ResponseEntity<Void> cadastrarContrato(@org.jetbrains.annotations.NotNull @RequestBody ContratoAcolhidoDTO contratoAcolhidoDTO) {
        try {
            CAservice.cadastrarContrato(contratoAcolhidoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode(), e.getMessage());
        }
    }
}