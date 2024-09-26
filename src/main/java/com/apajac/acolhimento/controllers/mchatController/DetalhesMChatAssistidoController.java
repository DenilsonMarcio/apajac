package com.apajac.acolhimento.controllers.mchatController;

import com.apajac.acolhimento.domain.dtos.DetalhesMChatAssistidoDTO;
import com.apajac.acolhimento.services.interfaces.ListarMChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/mchat")
public class DetalhesMChatAssistidoController {
    private final ListarMChatService listarMChatService;

    @GetMapping("/detalhes/{mchat_id}")
    public ResponseEntity<DetalhesMChatAssistidoDTO> listarDetalhesMChatPorAssistido(@PathVariable("mchat_id") Long mchat_id){
        try {
            DetalhesMChatAssistidoDTO response = listarMChatService.detalhesMChatPorAssistidoEData(mchat_id);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode(), e.getMessage());
        }
    }
}
