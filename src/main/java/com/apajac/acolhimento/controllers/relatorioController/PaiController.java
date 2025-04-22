package com.apajac.acolhimento.controllers.relatorioController;

import com.apajac.acolhimento.domain.dtos.relatorio.PorPaiDTO;
import com.apajac.acolhimento.domain.dtos.relatorio.ListaPorPaiDTO;
import com.apajac.acolhimento.repositories.AssistidoRepository;
import com.apajac.acolhimento.repositories.FamiliarRepository;
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
import java.util.Comparator;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/relatorio")
@Tag(name = "Relatórios", description = "Endpoints para geração de relatórios")
public class PaiController {

    private final AssistidoRepository assistidoService;
    private final FamiliarRepository paiService;

    @GetMapping("/listaporpai")
    public ResponseEntity<ListaPorPaiDTO> listarAssistidos(Pageable pageable){

        try {
            ListaPorPaiDTO assistidoResponse = new ListaPorPaiDTO();
            List<PorPaiDTO> listaPorPaiDTOS = new ArrayList<>();
            Page<Tuple> entities = assistidoService.ListaPorPaiA(pageable);

            for (Tuple entity : entities) {
                Boolean tempai = Boolean.FALSE;
                if (!(Boolean) paiService.ListaPorPaiC((Long) entity.get("id")).isEmpty()){
                    tempai = Boolean.TRUE;
                }
                listaPorPaiDTOS.add(PorPaiDTO.builder()
                        .id((Long) entity.get("id"))
                        .nome((String) entity.get("nome"))
                        .pai(tempai)
                        .build());
            }
            listaPorPaiDTOS.sort(Comparator.comparing(PorPaiDTO::getPai));
            boolean lastPage = entities.isLast();
            assistidoResponse.setIsLastPage(lastPage);
            assistidoResponse.setListaPorPai(listaPorPaiDTOS);

            return ResponseEntity.status(HttpStatus.OK).body(assistidoResponse);
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode(), e.getMessage());
        }
    }
}
