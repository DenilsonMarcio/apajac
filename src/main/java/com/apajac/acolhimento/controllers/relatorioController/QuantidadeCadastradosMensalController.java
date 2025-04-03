package com.apajac.acolhimento.controllers.relatorioController;

import com.apajac.acolhimento.domain.dtos.relatorio.CadastroMensalDTO;
import com.apajac.acolhimento.services.QuantidadeCadastradosMensalServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/relatorio")
@Tag(name = "Relatórios", description = "Endpoints para geração de relatórios")
public class QuantidadeCadastradosMensalController {

    private final QuantidadeCadastradosMensalServiceImpl quantidadeCadastradosMensalService;

    @GetMapping("/cadastro_mensal/{codAno}")
    public ResponseEntity<CadastroMensalDTO> quantidadeCadastradosMensal(@PathVariable("codAno") Integer codAno){
        try {
            CadastroMensalDTO dto = quantidadeCadastradosMensalService.cadastradosMensal(codAno);
            return ResponseEntity.ok().body(dto);
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode(), e.getMessage());
        }
    }
}
