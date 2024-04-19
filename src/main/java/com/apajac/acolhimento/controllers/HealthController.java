package com.apajac.acolhimento.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/health")
public class HealthController {

    @GetMapping
    public ResponseEntity<String> inicializarServidor(){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy - HH:mm:ss");
        LocalDateTime now = LocalDateTime.now(ZonedDateTime.now().withFixedOffsetZone().getZone());

        String message = "Aplicação Apajac rodando! - [" + now.format(formatter) + "]";
        return ResponseEntity.ok(message);
    }
}
