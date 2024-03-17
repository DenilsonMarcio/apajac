package br.com.apajac.acolhimento.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException (String message) {
        super(String.format(message));
    }
}

