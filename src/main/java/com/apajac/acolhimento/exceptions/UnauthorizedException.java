package com.apajac.acolhimento.exceptions;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(String.format(message));
    }
}
