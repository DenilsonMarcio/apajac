package com.apajac.acolhimento.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/health")
public class HealthController {

    @GetMapping
    public ResponseEntity<String> inicializarServidor(){
        try {
            ZoneId zoneId = ZoneId.systemDefault();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy - HH:mm:ss");
            LocalDateTime now = LocalDateTime.now(ZonedDateTime.now().getOffset());

            String message = "Aplicação Apajac rodando em "+zoneId+
                            "\n Data da solicitação " +now.format(formatter)+
                            "\n Respondendo com sucesso!!!]";

            return ResponseEntity.ok(message);
        } catch (Exception exception){
            throw new RuntimeException("Servido não esta respondendo! =>", exception);
        }
    }
}
