package com.apajac.acolhimento.advice;

import com.apajac.acolhimento.exceptions.BusinessException;
import com.apajac.acolhimento.exceptions.NotFoundException;
import com.apajac.acolhimento.exceptions.PermissionException;
import com.apajac.acolhimento.exceptions.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionAdvice extends ResponseEntityExceptionHandler {
    public static final String MESSAGE = "message";

    //Faz o tratamento das exceções do tipo "Não encontrado".
    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<Object> tratarExcecaoNotFoundException(final NotFoundException ex) {
        Map<String, String> body = new HashMap<>();
        body.put(MESSAGE, ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    //Faz o tratamento das exceções do tipo "Não autorizado".
    @ExceptionHandler(value = {HttpClientErrorException.class})
    public ResponseEntity<Object> tratarExcecaoHttpClientErrorException(final HttpClientErrorException ex) {
        Map<String, String> body = new HashMap<>();
        body.put(MESSAGE, ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }

    //Faz o tratamento das exceções do tipo "Má requisição".
    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<Object> tratarExcecaoIllegalArgumentException(final IllegalArgumentException ex) {
        Map<String, String> body = new HashMap<>();
        body.put(MESSAGE, ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    //Faz o tratamento das exceções do tipo "Genericas".
    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<Object> tratarExcecaoRuntimeException(final RuntimeException ex) {
        Map<String, String> body = new HashMap<>();
        body.put(MESSAGE, ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_GATEWAY);
    }

    //Faz o tratamento das exceções do tipo "Não Autenticado".
    @ExceptionHandler(value = {UnauthorizedException.class})
    public ResponseEntity<Object> tratarExcecaoUnauthorizedException(final UnauthorizedException ex) {
        Map<String, String> body = new HashMap<>();
        body.put(MESSAGE, ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }

    //Faz o tratamento das exceções do tipo "Não Permitido".
    @ExceptionHandler(value = {PermissionException.class})
    public ResponseEntity<Object> tratarPermissionException(final PermissionException ex) {
        Map<String, String> body = new HashMap<>();
        body.put(MESSAGE, ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = {BusinessException.class})
    public ResponseEntity<Object> tratarBusinessException(final BusinessException ex) {
        Map<String, String> body = new HashMap<>();
        body.put(MESSAGE, ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.NOT_ACCEPTABLE);
    }
}