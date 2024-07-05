package com.apajac.acolhimento.controllers.doadorController;

import com.apajac.acolhimento.domain.dtos.DoadorDTO;
import com.apajac.acolhimento.services.interfaces.PersistirDoadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/doador")
public class  CadastrarDoadorController {


    private final PersistirDoadorService doadorService;

    @PostMapping
    public ResponseEntity<String> cadastrarDoador(@RequestBody DoadorDTO doadorDTO) {

        doadorService.persistirDoador(doadorDTO);
        if (Objects.isNull(doadorDTO.getId())) {
            return ResponseEntity.ok("Doador cadastrado com sucesso.");
        }
        return ResponseEntity.ok("Doador atualizado com sucesso.");

    }

}
