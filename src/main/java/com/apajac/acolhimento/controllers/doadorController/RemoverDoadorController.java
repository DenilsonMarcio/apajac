package com.apajac.acolhimento.controllers.doadorController;

import com.apajac.acolhimento.services.DoadorServiceImpl;
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
    private final DoadorServiceImpl doadorService;

    @DeleteMapping("/{id}")
    public ResponseEntity<String> remover(@PathVariable Long id) {
        if (doadorService.buscarDoadorPorId(id).getId().equals(id)) {
            try
            {
                doadorService.removerDoador(id);
                return new ResponseEntity<>("{\n    \"message\": \"Doador removido com sucesso.\"\n}", HttpStatus.GONE);
            } catch (HttpClientErrorException e)
            {
                return ResponseEntity.status(e.getStatusCode()).body(String.format("Não foi possível remover doador com o id {%s}.",id));
            }
        } else
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Doador não encontrado.");
        }
    }
}
