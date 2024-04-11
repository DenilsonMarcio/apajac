package com.apajac.acolhimento.controllers.acolhidoController;

import com.apajac.acolhimento.services.interfaces.AtualizarStatusAcolhidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/acolhido")
public class AtualizarStatusAcolhidoController {

    private final AtualizarStatusAcolhidoService statusAcolhidoService;
    @PutMapping("/{id}/status_acolhido/{status}")
    public ResponseEntity<String> updateStatusAcolhido(@PathVariable("id") Long id, @PathVariable("status") Boolean status) {
        statusAcolhidoService.updateStatusAcolhido(id, status);
        return new ResponseEntity<>("Status alterado com sucesso!", HttpStatus.NO_CONTENT);
    }
}
