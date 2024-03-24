package br.com.apajac.acolhimento.controllers.acolhidoController;

import br.com.apajac.acolhimento.domain.dtos.AcolhidoDTO;
import br.com.apajac.acolhimento.domain.entities.AcolhidoEntity;
import br.com.apajac.acolhimento.mappers.AcolhidoMapper;
import br.com.apajac.acolhimento.repositories.AcolhidoRepository;
import br.com.apajac.acolhimento.services.interfaces.ConsultarAcolhidoService;
import br.com.apajac.acolhimento.services.interfaces.PersistirAcolhidoService;
import lombok.RequiredArgsConstructor;
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
    ResponseEntity<List<AcolhidoDTO>> listarAcolhidos(){
        try {
            List<AcolhidoEntity> entities = acolhidoService.listarAcolhidos();

            List<AcolhidoDTO> acolhidoDTO = acolhidoMapper.convertEntitesToDtos(entities);

            return ResponseEntity.status(HttpStatus.OK).body(acolhidoDTO);
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode(), e.getMessage());
        }
    }
}
