package com.apajac.acolhimento.controllers.doadorController;

import com.apajac.acolhimento.domain.entities.DoadorEntity;
import com.apajac.acolhimento.services.interfaces.ListarDoadorService;
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

    private final ListarDoadorService doadoresService;

    @GetMapping
    ResponseEntity<List<DoadorEntity>> listarDoadores(Pageable pageable){
        try
        {
            Page<DoadorEntity> entities = doadoresService.listarDoadores(pageable);
            return ResponseEntity.status(HttpStatus.OK).body(entities.getContent());
        } catch (HttpClientErrorException e)
        {
            throw new HttpClientErrorException(e.getStatusCode(), "Não foi possível listar doadores.");
        }
    }
}
