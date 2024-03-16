package br.com.apajac.acolhimento.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class HelloWordController {

    @GetMapping("/hello")
    public String hello(){
        return "Olá, Senhores aplicação Apajac já esta rodando!";
    }

}
