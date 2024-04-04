package br.com.apajac.acolhimento.controllers;

import br.com.apajac.acolhimento.domain.dtos.AcolhidoDTO;
import br.com.apajac.acolhimento.domain.dtos.EnderecoDTO;
import br.com.apajac.acolhimento.domain.entities.AcolhidoEntity;
import br.com.apajac.acolhimento.services.interfaces.AcolhidoService;
import br.com.apajac.acolhimento.services.interfaces.EnderecoService;
import br.com.apajac.acolhimento.services.interfaces.MaeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/acolhidos")
public class AcolhidoController {
    private final AcolhidoService acolhidoService;
    private final EnderecoService enderecoService;

    public AcolhidoController(AcolhidoService acolhidoService, EnderecoService enderecoService) {
        this.acolhidoService = acolhidoService;
        this.enderecoService = enderecoService;
    }

    @PostMapping
    public ResponseEntity<AcolhidoEntity> cadastrarAcolhido(@RequestBody AcolhidoDTO acolhidoDTO) {
        EnderecoDTO cadastrarendereco = acolhidoDTO.getEndereco();
        enderecoService.cadastrarEndereco(cadastrarendereco);
        AcolhidoEntity acolhido = acolhidoService.cadastrarAcolhido(acolhidoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(acolhido);
    }

}
