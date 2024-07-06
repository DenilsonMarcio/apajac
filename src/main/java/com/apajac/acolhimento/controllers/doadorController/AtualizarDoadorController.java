package com.apajac.acolhimento.controllers.doadorController;

import com.apajac.acolhimento.domain.dtos.DoadorDTO;
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

    private final com.apajac.acolhimento.services.interfaces.DoadorService DoadorService;

    @PutMapping
    public ResponseEntity<String> updateStatusDoador(@RequestBody DoadorDTO doadorDTO) {
        try
        {
            DoadorService.updateDoador(doadorDTO);
            return new ResponseEntity<>("{\n    \"message\": \"Doador alterado com sucesso!\"\n}", HttpStatus.OK);
        } catch(HttpClientErrorException e)
        {
            throw new HttpClientErrorException(e.getStatusCode(), "Não foi possível alterar doador.");
        }
    }
}
