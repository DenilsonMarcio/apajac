package com.apajac.acolhimento.exceptions;

public class PermissionException extends RuntimeException {
    public PermissionException (String message) {
        super(String.format(message));
    }
}