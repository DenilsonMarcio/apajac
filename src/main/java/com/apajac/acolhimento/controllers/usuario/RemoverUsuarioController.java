package com.apajac.acolhimento.controllers.usuario;

import com.apajac.acolhimento.services.UsuarioServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/usuario")
public class RemoverUsuarioController {
    private final UsuarioServiceImpl usuarioService;

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id){
        try {
            usuarioService.remover(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode(), e.getMessage());
        }
    }
}
