package com.apajac.acolhimento.controllers.relatorioController;

import com.apajac.acolhimento.domain.dtos.relatorio.PorBairroDTO;
import com.apajac.acolhimento.domain.dtos.relatorio.ListaPorBairroDTO;
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
public class ListaPorBairroController {

    private final AssistidoRepository assistidoService;

    @GetMapping("/listaporbairro")
    public ResponseEntity<ListaPorBairroDTO> listarAssistidos(Pageable pageable){

        try {
            ListaPorBairroDTO assistidoResponse = new ListaPorBairroDTO();
            List<PorBairroDTO> listaPorBairroDTOS= new ArrayList<>();
            Page<Tuple> entities = assistidoService.ListaAssistidosPorBairro(pageable);

            for (Tuple entity : entities) {
                listaPorBairroDTOS.add(PorBairroDTO.builder()
                        .id((Long) entity.get("id"))
                        .nome((String) entity.get("nome"))
                        .bairro((String) entity.get("bairro"))
                        .build());
            }

            boolean lastPage = entities.isLast();
            assistidoResponse.setIsLastPage(lastPage);
            assistidoResponse.setListaPorBairro(listaPorBairroDTOS);

            return ResponseEntity.status(HttpStatus.OK).body(assistidoResponse);
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode(), e.getMessage());
        }
    }
}
