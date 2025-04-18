package com.apajac.acolhimento.controllers.relatorioController;

import com.apajac.acolhimento.domain.dtos.relatorio.PorPermanenciaDTO;
import com.apajac.acolhimento.domain.dtos.relatorio.ListaPorPermanenciaDTO;
import com.apajac.acolhimento.repositories.AssistidoRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.Tuple;
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

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/relatorio")
@Tag(name = "Relatórios", description = "Endpoints para geração de relatórios")
public class PermanenciaController {

    private final AssistidoRepository assistidoService;

    @GetMapping("/listaporpermanencia")
    public ResponseEntity<ListaPorPermanenciaDTO> listarAssistidos(Pageable pageable){

        try {
            ListaPorPermanenciaDTO assistidoResponse = new ListaPorPermanenciaDTO();
            List<PorPermanenciaDTO> listaPorPermanenciaDTOS= new ArrayList<>();
            Page<Tuple> entities = assistidoService.ListaPorPermanencia(pageable);

            for (Tuple entity : entities) {
                listaPorPermanenciaDTOS.add(PorPermanenciaDTO.builder()
                        .id((Long) entity.get("id"))
                        .nome((String) entity.get("nome"))
                        .tempoP((String) entity.get("tempoP"))
                        .build());
            }

            boolean lastPage = entities.isLast();
            assistidoResponse.setIsLastPage(lastPage);
            assistidoResponse.setListaPorPermanencia(listaPorPermanenciaDTOS);

            return ResponseEntity.status(HttpStatus.OK).body(assistidoResponse);
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode(), e.getMessage());
        }
    }
}
