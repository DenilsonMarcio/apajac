package com.apajac.acolhimento.controllers.acolhidoController;

import com.apajac.acolhimento.domain.dtos.AcolhidoDTO;
import com.apajac.acolhimento.domain.entities.AcolhidoEntity;
import com.apajac.acolhimento.mappers.AcolhidoMapper;
import com.apajac.acolhimento.services.interfaces.ConsultarAcolhidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/acolhido")
public class BuscarAcolhidoPorIdController {

    private final ConsultarAcolhidoService acolhidoService;

    private final AcolhidoMapper acolhidoMapper;
    @GetMapping("/por_id/{id}")
    ResponseEntity<AcolhidoDTO> buscarAcolhido(@PathVariable("id") Long id){
        try {
            AcolhidoEntity acolhido = acolhidoService.buscarAcolhidoPorId(id);

            AcolhidoDTO acolhidoDTO = acolhidoMapper.convertEntityToDto(acolhido);

            return ResponseEntity.status(HttpStatus.OK).body(acolhidoDTO);
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode(), e.getMessage());
        }
    }
}
