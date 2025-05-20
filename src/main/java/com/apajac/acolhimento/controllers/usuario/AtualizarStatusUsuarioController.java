package com.apajac.acolhimento.controllers.usuario;

import com.apajac.acolhimento.services.interfaces.AtualizarStatusUsuarioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/usuario")
@Tag(name = "Usuários", description = "Endpoints para gerenciamento de usuários")
public class AtualizarStatusUsuarioController {
    private final AtualizarStatusUsuarioService atualizarStatusUsuarioService;
    @PutMapping("/{id}/status/{id_responsavel}")
    public ResponseEntity<String> updateStatusUsuario(@PathVariable("id") Long id, @PathVariable("id_responsavel") Long id_responsavel) {
        atualizarStatusUsuarioService.updateStatusUsuario(id, id_responsavel);
        return new ResponseEntity<>("Status alterado com sucesso!", HttpStatus.NO_CONTENT);
    }
}
