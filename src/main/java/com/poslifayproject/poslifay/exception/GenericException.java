package com.poslifayproject.poslifay.exception;

public class GenericException extends RuntimeException {
    public GenericException(String message) {
        super(message);
    }

    public GenericException(String message, Throwable cause) {
        super(message, cause);
    }
}
