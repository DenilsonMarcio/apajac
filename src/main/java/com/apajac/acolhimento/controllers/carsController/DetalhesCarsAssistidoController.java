package com.apajac.acolhimento.controllers.carsController;

import com.apajac.acolhimento.domain.dtos.DetalhesCarsAssistidoDTO;
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
public class DetalhesCarsAssistidoController {
    private final ListarCarsService listarCarsService;

    @GetMapping("/detalhes/{cars_id}")
    public ResponseEntity<DetalhesCarsAssistidoDTO> listarDetalhesCarsPorAssistido(@PathVariable("cars_id") Long cars_id){
        try {
            DetalhesCarsAssistidoDTO response = listarCarsService.detalhesCarsPorAssistidoEData(cars_id);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode(), e.getMessage());
        }
    }
}
