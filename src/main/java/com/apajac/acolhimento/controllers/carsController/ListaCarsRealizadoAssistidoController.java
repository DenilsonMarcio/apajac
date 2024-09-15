package com.apajac.acolhimento.controllers.carsController;

import com.apajac.acolhimento.domain.dtos.NomeAssistidoCarsDTO;
import com.apajac.acolhimento.services.interfaces.ListarCarsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/cars")
public class ListaCarsRealizadoAssistidoController {

    private final ListarCarsService listarCarsService;

    @GetMapping("/{assistido_id}")
    public ResponseEntity<NomeAssistidoCarsDTO> listarCarsPorAssistido(@PathVariable("assistido_id") Long assistido_id){
        try {
            NomeAssistidoCarsDTO response = listarCarsService.listarCarsPorAssistido(assistido_id);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode(), e.getMessage());
        }
    }
}
