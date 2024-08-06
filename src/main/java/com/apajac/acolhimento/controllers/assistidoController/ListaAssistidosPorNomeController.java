package com.apajac.acolhimento.controllers.assistidoController;

import com.apajac.acolhimento.domain.dtos.AssistidoSimplificadoDTO;
import com.apajac.acolhimento.domain.dtos.ListaAssistidoDTO;
import com.apajac.acolhimento.domain.entities.AssistidoEntity;
import com.apajac.acolhimento.mappers.AssistidoMapper;
import com.apajac.acolhimento.services.interfaces.ConsultarAssistidoService;
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
@RequestMapping("/lista_assistidos_por_nome")
public class ListaAssistidosPorNomeController {

    private final ConsultarAssistidoService assistidoService;

    private final AssistidoMapper assistidoMapper;
    @GetMapping("/{nome}")
    public ResponseEntity<ListaAssistidoDTO> buscarAssistidosPorNome(@PathVariable("nome") String nome, Pageable pageable){
        try {
            ListaAssistidoDTO assistidoResponse = new ListaAssistidoDTO();
            Page<AssistidoEntity> assistidos = assistidoService.buscarAssistidosPorNome(nome, pageable);

            boolean lastPage = assistidos.isLast();
            String paramSort = pageable.getSort().toString();

            List<AssistidoSimplificadoDTO> assistidosDTO = assistidoMapper.convertEntitesToDtos(assistidos.getContent(), paramSort);
            assistidoResponse.setIsLastPage(lastPage);
            assistidoResponse.setAssistidos(assistidosDTO);

            return ResponseEntity.status(HttpStatus.OK).body(assistidoResponse);
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode(), e.getMessage());
        }
    }
}
