package com.apajac.acolhimento.controllers.assistidoController;

import com.apajac.acolhimento.domain.dtos.AssistidoDTO;
import com.apajac.acolhimento.domain.entities.AssistidoEntity;
import com.apajac.acolhimento.mappers.AssistidoMapper;
import com.apajac.acolhimento.services.interfaces.ConsultarAssistidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/assistido")
public class BuscarAssistidoPorIdController {

    private final ConsultarAssistidoService assistidoService;

    private final AssistidoMapper assistidoMapper;
    @GetMapping("/por_id/{id}")
    public ResponseEntity<AssistidoDTO> buscarAssistido(@PathVariable("id") Long id){
        try {
            AssistidoEntity assistido = assistidoService.buscarAssistidoPorId(id);

            AssistidoDTO assistidoDTO = assistidoMapper.convertEntityToDto(assistido);

            return ResponseEntity.status(HttpStatus.OK).body(assistidoDTO);
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode(), e.getMessage());
        }
    }
}
