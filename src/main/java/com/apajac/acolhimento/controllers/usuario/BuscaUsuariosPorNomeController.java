package com.apajac.acolhimento.controllers.usuario;

import com.apajac.acolhimento.domain.dtos.ListaUsuarioDTO;
import com.apajac.acolhimento.domain.dtos.UsuarioSemSenhaDTO;
import com.apajac.acolhimento.domain.entities.UsuarioEntity;
import com.apajac.acolhimento.mappers.UsuarioMapper;
import com.apajac.acolhimento.services.interfaces.UsuarioService;
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
@RequestMapping("/lista_usuarios_por_nome")
public class BuscaUsuariosPorNomeController {

    private final UsuarioService usuarioService;

    private final UsuarioMapper usuarioMapper;
    @GetMapping("/{nome}")
    ResponseEntity<ListaUsuarioDTO> buscarUsuariosPorNome(@PathVariable("nome") String nome, Pageable pageable){
        try {
            ListaUsuarioDTO usuarioResponse = new ListaUsuarioDTO();
            Page<UsuarioEntity> entities = usuarioService.buscarUsuariosPorNome(nome, pageable);

            boolean lastPage = entities.isLast();

            List<UsuarioSemSenhaDTO> response = usuarioMapper.convertEntitesToDtos(entities.getContent());
            usuarioResponse.setIsLastPage(lastPage);
            usuarioResponse.setUsuarios(response);

            return ResponseEntity.status(HttpStatus.OK).body(usuarioResponse);
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode(), e.getMessage());
        }
    }

}
