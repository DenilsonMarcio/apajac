package com.apajac.acolhimento.controllers.doadorController;

import com.apajac.acolhimento.domain.dtos.DoadorDTO;
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
public class CadastrarDoadorController
{
    private final DoadorService doadorService;

    @PostMapping
    public ResponseEntity<String> createDoador(@RequestBody DoadorDTO doadorDTO) {
        try
        {
            doadorService.createDoador(doadorDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Doador cadastrado com sucesso.");
        } catch(HttpClientErrorException e)
        {
            throw new HttpClientErrorException(e.getStatusCode(),"Não foi possível cadastrar doador.");
        }
    }
}