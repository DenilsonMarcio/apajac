package com.apajac.acolhimento.controllers.mchatController;

import com.apajac.acolhimento.domain.dtos.MChatDTO;
import com.apajac.acolhimento.exceptions.BusinessException;
import com.apajac.acolhimento.services.interfaces.RealizarMChatAssistidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/mchat")
public class RealizarMChatController {
    private final RealizarMChatAssistidoService realizarMChatAssistidoService;

    @PostMapping
    private ResponseEntity<String> realizarMChatAssistido(@Valid @RequestBody MChatDTO mChatDTO) {
        try {
            realizarMChatAssistidoService.realizarMChatAssistido(mChatDTO);
            return new ResponseEntity<>("M-Chat realizado com sucesso.", HttpStatus.CREATED);
        } catch (BusinessException e) {
            throw new BusinessException(e.getMessage());
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode(), e.getMessage());
        }
    }
}
