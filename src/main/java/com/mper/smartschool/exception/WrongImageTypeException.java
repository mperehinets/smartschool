package com.mper.smartschool.exception;

public class WrongImageTypeException extends RuntimeException {
    public WrongImageTypeException(String message) {
        super(message);
    }

    public WrongImageTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
