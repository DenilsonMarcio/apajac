package com.apajac.acolhimento.controllers.doadorController;

import com.apajac.acolhimento.domain.dtos.DoadorDTO;
import com.apajac.acolhimento.services.interfaces.PersistirDoadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/doador")
public class  CadastrarDoadorController {


    private final PersistirDoadorService doadorService;

    @PostMapping
    public ResponseEntity<String> createDoador(@RequestBody DoadorDTO doadorDTO) {

        doadorService.persistirDoador(doadorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Doador cadastrado com sucesso.");

    }

}
