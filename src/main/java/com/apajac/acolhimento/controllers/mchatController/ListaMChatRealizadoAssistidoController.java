package com.apajac.acolhimento.controllers.mchatController;

import com.apajac.acolhimento.domain.dtos.NomeAssistidoMChatDTO;
import com.apajac.acolhimento.services.interfaces.ListarMChatService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/mchat")
@Tag(name = "Avaliações", description = "Endpoints para gerenciamento das avaliações")
public class ListaMChatRealizadoAssistidoController {
    private final ListarMChatService listarMChatService;

    @GetMapping("/{assistido_id}")
    public ResponseEntity<NomeAssistidoMChatDTO> listarMChatPorAssistido(@PathVariable("assistido_id") Long assistido_id){
        try {
            NomeAssistidoMChatDTO response = listarMChatService.listarMChatPorAssistido(assistido_id);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode(), e.getMessage());
        }
    }
}
