package com.apajac.acolhimento.controllers.acolhidoController;

import com.apajac.acolhimento.domain.dtos.AcolhidoDTO;
import com.apajac.acolhimento.services.interfaces.PersistirAcolhidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/acolhido")
public class CadastrarAcolhidoController {

    private final PersistirAcolhidoService acolhidoService;
    @PostMapping
    public ResponseEntity<String> createAcolhido(@RequestBody AcolhidoDTO acolhidoDTO) {
        acolhidoService.persistirAcolhido(acolhidoDTO);
        if (Objects.isNull(acolhidoDTO.getId())){
            return new ResponseEntity<>("Acolhido cadastrado com sucesso!", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Acolhido atualizado com sucesso!", HttpStatus.ACCEPTED);
    }
}
