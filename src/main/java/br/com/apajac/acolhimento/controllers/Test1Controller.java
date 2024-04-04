package br.com.apajac.acolhimento.controllers;

import br.com.apajac.acolhimento.domain.dtos.AcolhidoDTO;
import br.com.apajac.acolhimento.domain.dtos.ParenteDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test1")
public class Test1Controller {


    @PostMapping
    public void test1(@RequestBody ParenteDTO parenteDTO)
    {
        System.out.println(parenteDTO);
    }
}
