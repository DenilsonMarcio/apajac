package com.apajac.acolhimento.controllers.doadorController;

import com.apajac.acolhimento.domain.dtos.DoadorDTO;
import com.apajac.acolhimento.domain.entities.DoadorEntity;
import com.apajac.acolhimento.mappers.DoadorMapper;
import com.apajac.acolhimento.services.interfaces.ConsultarDoadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/doador")
public class BuscarDoadorPorIDController {

    private final ConsultarDoadorService consultarDoadorService;
    private final DoadorMapper doadorMapper;

    @GetMapping("/{id}")
    public ResponseEntity<DoadorDTO> buscarDoadorPorId(@PathVariable Long id) {
        try {
            DoadorEntity doadorEntity = consultarDoadorService.buscarDoadorPorId(id);
            DoadorDTO doadorDTO = doadorMapper.mapEntityToDTO(doadorEntity);
            return ResponseEntity.status(HttpStatus.OK).body(doadorDTO);
        } catch (HttpClientErrorException e)
        {
            throw new HttpClientErrorException(e.getStatusCode(), String.format("Não foi possível buscar doador com o id {%s}.",id.toString()));
        }
    }
}
