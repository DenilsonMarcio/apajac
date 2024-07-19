package com.apajac.acolhimento.controllers.doadorController;

import com.apajac.acolhimento.services.interfaces.DoadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/doador")
public class RemoverDoadorController {

    private final DoadorService doadorService;

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removerDoador(@PathVariable Long id) {
        try
        {
            doadorService.remover(id);
            return ResponseEntity.status(HttpStatus.GONE).body("Doador removido com sucesso.");
        } catch (HttpClientErrorException e)
        {
            return ResponseEntity.status(e.getStatusCode()).body(String.format("Não foi possível remover doador com o id {%s}.",id));
        }
    }

}

