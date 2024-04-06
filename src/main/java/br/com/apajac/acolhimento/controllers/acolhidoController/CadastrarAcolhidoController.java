package br.com.apajac.acolhimento.controllers.acolhidoController;

import br.com.apajac.acolhimento.domain.dtos.AcolhidoDTO;
import br.com.apajac.acolhimento.services.interfaces.PersistirAcolhidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/acolhido")
public class CadastrarAcolhidoController {

    private final PersistirAcolhidoService acolhidoService;
    @PostMapping
    public ResponseEntity<String> createAcolhido(@RequestBody AcolhidoDTO acolhidoDTO) {
        acolhidoService.persistirAcolhido(acolhidoDTO);
        return new ResponseEntity<>("Acolhido cadastrado com sucesso!", HttpStatus.CREATED);
    }
}
