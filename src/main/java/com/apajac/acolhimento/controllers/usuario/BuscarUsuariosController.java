package com.apajac.acolhimento.controllers.usuario;

import com.apajac.acolhimento.domain.dtos.UsuarioSemSenhaDTO;
import com.apajac.acolhimento.domain.entities.UsuarioEntity;
import com.apajac.acolhimento.mappers.UsuarioMapper;
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
    private final UsuarioMapper usuarioMapper;
    @GetMapping
    ResponseEntity<List<UsuarioSemSenhaDTO>> listarUsuarios(){
        try {
            List<UsuarioEntity> entities = usuarioService.listarUsuarios();
            List<UsuarioSemSenhaDTO> usuarioSemSenhaDTOS = usuarioMapper.convertEntitesToDtos(entities);
            return ResponseEntity.status(HttpStatus.OK).body(usuarioSemSenhaDTOS);
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode(), e.getMessage());
        }
    }
}
