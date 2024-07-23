package com.apajac.acolhimento.controllers.doadorController;

import com.apajac.acolhimento.domain.dtos.DoadorDTO;
import com.apajac.acolhimento.services.interfaces.EditarDoadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/doador")
public class EditarDoadorController {

    private final EditarDoadorService doadorService;

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarDoador(@RequestBody DoadorDTO doadorDTO) {

        try {
            doadorService.editarDoador(doadorDTO);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Doador Alterado com sucesso.");
        } catch(HttpClientErrorException e){
            throw new HttpClientErrorException(e.getStatusCode(), "Não foi possível alterar doador.");
        }

    }

}
