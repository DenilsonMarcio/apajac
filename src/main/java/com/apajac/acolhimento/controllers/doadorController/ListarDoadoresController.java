package com.apajac.acolhimento.controllers.doadorController;

import com.apajac.acolhimento.domain.dtos.DoadorDTO;
import com.apajac.acolhimento.domain.entities.DoadorEntity;
import com.apajac.acolhimento.mappers.DoadorMapper;
import com.apajac.acolhimento.services.interfaces.DoadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/doadores")
public class ListarDoadoresController {

    private final DoadorService doadoresService;

    private final DoadorMapper doadoresMapper;
    @GetMapping
    ResponseEntity<List<DoadorDTO>> listarDoadores(Pageable pageable){
        try
        {
            Page<DoadorEntity> entities = doadoresService.listarDoadores(pageable);
            List<DoadorDTO> doadoresDTO = doadoresMapper.convertEntitiesToDtos(entities.getContent());
            return ResponseEntity.status(HttpStatus.OK).body(doadoresDTO);
        } catch (HttpClientErrorException e)
        {
            throw new HttpClientErrorException(e.getStatusCode(), "Não foi possível listar doadores.");
        }
    }
}
