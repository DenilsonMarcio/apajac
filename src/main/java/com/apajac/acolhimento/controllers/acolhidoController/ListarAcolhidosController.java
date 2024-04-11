package com.apajac.acolhimento.controllers.acolhidoController;

import com.apajac.acolhimento.domain.dtos.AcolhidoSimplificadoDTO;
import com.apajac.acolhimento.domain.entities.AcolhidoEntity;
import com.apajac.acolhimento.mappers.AcolhidoMapper;
import com.apajac.acolhimento.services.interfaces.ConsultarAcolhidoService;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/lista-acolhidos")
public class ListarAcolhidosController {

    private final ConsultarAcolhidoService acolhidoService;

    private final AcolhidoMapper acolhidoMapper;
    @GetMapping
    ResponseEntity<List<AcolhidoSimplificadoDTO>> listarAcolhidos(Pageable pageable){
        try {
            List<AcolhidoEntity> entities = acolhidoService.listarAcolhidos(pageable).getContent();

            List<AcolhidoSimplificadoDTO> acolhidoDTO = acolhidoMapper.convertEntitesToDtos(entities);

            return ResponseEntity.status(HttpStatus.OK).body(acolhidoDTO);
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode(), e.getMessage());
        }
    }
}
