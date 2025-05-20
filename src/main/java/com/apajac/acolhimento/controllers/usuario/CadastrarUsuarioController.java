package com.apajac.acolhimento.controllers.usuario;

import com.apajac.acolhimento.domain.dtos.UsuarioDTO;
import com.apajac.acolhimento.exceptions.BusinessException;
import com.apajac.acolhimento.services.UsuarioServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/usuario")
@Tag(name = "Usuários", description = "Endpoints para gerenciamento de usuários")
public class CadastrarUsuarioController {

    private final UsuarioServiceImpl usuarioService;

    @PostMapping
    public ResponseEntity<String> inserir(@Valid @RequestBody UsuarioDTO usuarioDTO, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                throw new RuntimeException("Falha ao cadastrar o Usuário.");
            }
            usuarioService.cadastrar(usuarioDTO);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (BusinessException e) {
            throw new BusinessException(e.getMessage());
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode(), e.getMessage());
        }
    }
}
