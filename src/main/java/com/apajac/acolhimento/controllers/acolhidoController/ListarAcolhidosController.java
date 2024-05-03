package com.apajac.acolhimento.controllers.acolhidoController;

import com.apajac.acolhimento.domain.dtos.AcolhidoSimplificadoDTO;
import com.apajac.acolhimento.domain.dtos.ListaAcolhidoDTO;
import com.apajac.acolhimento.domain.entities.AcolhidoEntity;
import com.apajac.acolhimento.mappers.AcolhidoMapper;
import com.apajac.acolhimento.services.interfaces.ConsultarAcolhidoService;
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
@RequestMapping("/lista-acolhidos")
public class ListarAcolhidosController {

    private final ConsultarAcolhidoService acolhidoService;

    private final AcolhidoMapper acolhidoMapper;
    @GetMapping
    ResponseEntity<ListaAcolhidoDTO> listarAcolhidos(Pageable pageable){
        try {
            ListaAcolhidoDTO acolhidoResponse = new ListaAcolhidoDTO();
            Page<AcolhidoEntity> entities = acolhidoService.listarAcolhidos(pageable);

            boolean lastPage = entities.isLast();
            String paramSort = pageable.getSort().toString();

            List<AcolhidoSimplificadoDTO> acolhidoDTO = acolhidoMapper.convertEntitesToDtos(entities.getContent(), paramSort);
            acolhidoResponse.setIsLastPage(lastPage);
            acolhidoResponse.setAcolhidos(acolhidoDTO);

            return ResponseEntity.status(HttpStatus.OK).body(acolhidoResponse);
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode(), e.getMessage());
        }
    }
}