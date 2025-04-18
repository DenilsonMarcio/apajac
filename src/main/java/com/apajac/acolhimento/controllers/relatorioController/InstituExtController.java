package com.apajac.acolhimento.controllers.relatorioController;

import com.apajac.acolhimento.domain.dtos.relatorio.ListaPorInstituExtDTO;
import com.apajac.acolhimento.domain.dtos.relatorio.PorInstituExtDTO;
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
public class InstituExtController {

    private final AssistidoRepository assistidoService;

    @GetMapping("/listaporinstituext")
    public ResponseEntity<ListaPorInstituExtDTO> listarAssistidos(Pageable pageable){

        try {
            ListaPorInstituExtDTO assistidoResponse = new ListaPorInstituExtDTO();
            List<PorInstituExtDTO> listaPorInstituiExtDTOS= new ArrayList<>();
            Page<Tuple> entities = assistidoService.ListaPorInstituiExt(pageable);

            for (Tuple entity : entities) {
                listaPorInstituiExtDTOS.add(PorInstituExtDTO.builder()
                        .id((Long) entity.get("id"))
                        .nome((String) entity.get("nome"))
                        .instituext((String) entity.get("bairro"))
                        .build());
            }

            boolean lastPage = entities.isLast();
            assistidoResponse.setIsLastPage(lastPage);
            assistidoResponse.setListaPorInstituExt(listaPorInstituiExtDTOS);

            return ResponseEntity.status(HttpStatus.OK).body(assistidoResponse);
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode(), e.getMessage());
        }
    }
}
