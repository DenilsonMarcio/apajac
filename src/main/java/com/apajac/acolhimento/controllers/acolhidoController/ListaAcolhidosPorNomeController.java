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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/lista-acolhidos-por-nome")
public class ListaAcolhidosPorNomeController {

    private final ConsultarAcolhidoService acolhidoService;

    private final AcolhidoMapper acolhidoMapper;
    @GetMapping("/{nome}")
    ResponseEntity<ListaAcolhidoDTO> buscarAcolhidosPorNome(@PathVariable("nome") String nome, Pageable pageable){
        try {
            ListaAcolhidoDTO acolhidoResponse = new ListaAcolhidoDTO();
            Page<AcolhidoEntity> acolhidos = acolhidoService.buscarAcolhidosPorNome(nome, pageable);

            boolean lastPage = acolhidos.isLast();
            String paramSort = pageable.getSort().toString();

            List<AcolhidoSimplificadoDTO> acolhidosDTO = acolhidoMapper.convertEntitesToDtos(acolhidos.getContent(), paramSort);
            acolhidoResponse.setIsLastPage(lastPage);
            acolhidoResponse.setAcolhidos(acolhidosDTO);

            return ResponseEntity.status(HttpStatus.OK).body(acolhidoResponse);
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode(), e.getMessage());
        }
    }
}
