package com.apajac.acolhimento.controllers.assistidoController;

import com.apajac.acolhimento.services.interfaces.AtualizarStatusAssistidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/assistido")
public class AtualizarStatusAssistidoController {

    private final AtualizarStatusAssistidoService statusAssistidoService;
    @PutMapping("/{id}/status_assistido/{status}/{id_responsavel}")
    public ResponseEntity<String> updateStatusAcolhido(
                @PathVariable("id") Long id,
                @PathVariable("status") Boolean status,
                @PathVariable("id_responsavel") Long id_responsavel) {
        statusAssistidoService.updateStatusAssistido(id, status, id_responsavel);
        return new ResponseEntity<>("Status alterado com sucesso!", HttpStatus.NO_CONTENT);
    }
}
