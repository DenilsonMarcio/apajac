package com.apajac.acolhimento.controllers.carsController;

import com.apajac.acolhimento.domain.dtos.CarsDTO;
import com.apajac.acolhimento.exceptions.BusinessException;
import com.apajac.acolhimento.services.interfaces.RealizarCarsAssistidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/cars")
public class RealizarCarsController {

    private final RealizarCarsAssistidoService realizarCarsAssistidoService;

    @PostMapping
    public ResponseEntity<String> realizarCarsAssistido(@Valid @RequestBody CarsDTO carsDTO) {
        try {
            realizarCarsAssistidoService.realizarCarsAssistido(carsDTO);
            return new ResponseEntity<>("Cars realizado com sucesso.",HttpStatus.CREATED);
        } catch (BusinessException e) {
            throw new BusinessException(e.getMessage());
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode(), e.getMessage());
        }
    }
}
