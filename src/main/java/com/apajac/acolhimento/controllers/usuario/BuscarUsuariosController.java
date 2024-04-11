package com.apajac.acolhimento.controllers.usuario;

import com.apajac.acolhimento.domain.entities.UsuarioEntity;
import com.apajac.acolhimento.services.UsuarioServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/usuario")
public class BuscarUsuariosController {

    private final UsuarioServiceImpl usuarioService;
    @GetMapping
    ResponseEntity<List<UsuarioEntity>> listarUsuarios(){
        try {
            List<UsuarioEntity> entities = usuarioService.listarUsuarios();
            return ResponseEntity.status(HttpStatus.OK).body(entities);
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode(), e.getMessage());
        }
    }
}
