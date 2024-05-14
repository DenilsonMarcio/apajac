package com.apajac.acolhimento.controllers.usuario;

import com.apajac.acolhimento.services.interfaces.AtualizarStatusUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/usuario")
public class AtualizarStatusUsuarioController {
    private final AtualizarStatusUsuarioService atualizarStatusUsuarioService;
    @PutMapping("/{id}/status_acolhido/{status}")
    public ResponseEntity<String> updateStatusUsuario(@PathVariable("id") Long id, @PathVariable("status") Boolean status) {
        atualizarStatusUsuarioService.updateStatusUsuario(id, status);
        return new ResponseEntity<>("Status alterado com sucesso!", HttpStatus.NO_CONTENT);
    }
}
