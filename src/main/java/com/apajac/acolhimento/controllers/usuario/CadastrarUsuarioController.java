package com.apajac.acolhimento.controllers.usuario;

import com.apajac.acolhimento.domain.dtos.UsuarioDTO;
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
public class CadastrarUsuarioController {

    private final UsuarioServiceImpl usuarioService;
    @PostMapping
    public ResponseEntity<Void> inserir(@RequestBody UsuarioDTO usuarioDTO){
        try {
            usuarioService.cadastrar(usuarioDTO);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode(), e.getMessage());
        }
    }
}
