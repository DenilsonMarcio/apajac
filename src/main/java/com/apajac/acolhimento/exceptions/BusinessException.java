package com.apajac.acolhimento.exceptions;

public class BusinessException extends RuntimeException {

    private BusinessExceptionMessage messageObject;

    public BusinessException (String message) {
        super(String.format(message));
    }

    public BusinessException (String message, Exception exception) {
        super(String.format(message, exception));
    }
    public BusinessException(BusinessExceptionMessage message) {
        super(message.getMessage());
        this.messageObject = message;
    }

    public BusinessExceptionMessage getMessageObject() {
        return messageObject;
    }

}