package com.apajac.acolhimento.controllers.relatorioController;

import com.apajac.acolhimento.domain.dtos.relatorio.CadastroMensalDTO;
import com.apajac.acolhimento.services.QuantidadeCadastradosMensalServiceImpl;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/relatorio")
public class QuantidadeCadastradosMensalController {

    private final QuantidadeCadastradosMensalServiceImpl quantidadeCadastradosMensalService;

    @GetMapping("/cadastro_mensal")
    public ResponseEntity<List<CadastroMensalDTO>> quantidadeCadastradosMensal(){
        try {
            List<CadastroMensalDTO> cadastroMensalDTOS = quantidadeCadastradosMensalService.cadastradosMensal();
            return ResponseEntity.ok().body(cadastroMensalDTOS);
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode(), e.getMessage());
        }
    }
}
