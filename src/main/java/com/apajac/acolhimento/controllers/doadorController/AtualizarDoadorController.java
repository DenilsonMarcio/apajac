package com.apajac.acolhimento.controllers.doadorController;

import com.apajac.acolhimento.domain.dtos.DoadorDTO;
import com.apajac.acolhimento.services.interfaces.AtualizarDoadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/doador")
public class AtualizarDoadorController {

    private final AtualizarDoadorService atualizarDoadorService;

    @PutMapping("/{id}")
    public ResponseEntity<String> updateStatusDoador(
            @PathVariable Long id,
            @RequestBody DoadorDTO doadorDTO) {
        try {
            atualizarDoadorService.updateDoador(id, doadorDTO);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Doador alterado com sucesso!");
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode(), "Não foi possível alterar doador.");
        }
    }
}
