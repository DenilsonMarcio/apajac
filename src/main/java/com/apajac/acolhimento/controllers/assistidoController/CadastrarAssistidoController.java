package com.apajac.acolhimento.controllers.assistidoController;

import com.apajac.acolhimento.domain.dtos.AssistidoDTO;
import com.apajac.acolhimento.services.interfaces.PersistirAssistidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/assistido")
public class CadastrarAssistidoController {

    private final PersistirAssistidoService assistidoService;
    @PostMapping
    public ResponseEntity<String> createAssistido(@RequestBody AssistidoDTO assistidoDTO) {
        assistidoService.persistirAssistido(assistidoDTO);
        if (Objects.isNull(assistidoDTO.getId())){
            return new ResponseEntity<>("Assistido cadastrado com sucesso!", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Assistido atualizado com sucesso!", HttpStatus.ACCEPTED);
    }
}
