package com.apajac.acolhimento.controllers.doadorController;

import com.apajac.acolhimento.domain.dtos.DoadorDTO;
import com.apajac.acolhimento.domain.entities.DoadorEntity;
import com.apajac.acolhimento.services.interfaces.ConsultarDoadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.apajac.acolhimento.mappers.DoadorMapper;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/doador")
public class ListaDoadoresController {

    private final ConsultarDoadorService consultarDoadorService;
    private final DoadorMapper doadorMapper;


    @GetMapping("/listar")
    public ResponseEntity<Page<DoadorDTO>> listarDoadoresPage(Pageable pageable) {
        try {
            Page<DoadorEntity> doadorEntities = consultarDoadorService.listarDoadoresPage(pageable);
            Page<DoadorDTO> doadorDTOS = doadorEntities.map(doadorMapper::mapEntityToDTO);
            return ResponseEntity.status(HttpStatus.OK).body(doadorDTOS);
        }catch (HttpClientErrorException e)
        {
            throw new HttpClientErrorException(e.getStatusCode(), "Não foi possivel listar doadores.");
        }
    }
}
