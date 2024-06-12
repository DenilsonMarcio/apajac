package com.apajac.acolhimento.exceptions;

public class BusinessExceptionMessage extends RuntimeException {
    private String message;

    public BusinessExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
