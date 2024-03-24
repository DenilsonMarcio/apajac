package br.com.apajac.acolhimento.controllers.acolhidoController;

import br.com.apajac.acolhimento.domain.dtos.AcolhidoDTO;
import br.com.apajac.acolhimento.domain.entities.AcolhidoEntity;
import br.com.apajac.acolhimento.mappers.AcolhidoMapper;
import br.com.apajac.acolhimento.services.interfaces.ConsultarAcolhidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/acolhido/{id}")
public class BuscarAcolhidoPorIdController {

    private final ConsultarAcolhidoService acolhidoService;

    private final AcolhidoMapper acolhidoMapper;
    @GetMapping
    ResponseEntity<AcolhidoDTO> buscarAcolhido(@PathVariable("id") Long id){
        try {
            AcolhidoEntity acolhido = acolhidoService.buscarAcolhidoPorId(id);

            AcolhidoDTO acolhidoDTO = acolhidoMapper.convertEntityToDto(acolhido);

            return ResponseEntity.status(HttpStatus.OK).body(acolhidoDTO);
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode(), e.getMessage());
        }
    }
}
