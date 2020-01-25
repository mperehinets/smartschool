package com.mper.smartschool.exception;

public class SchoolFilledByClassesException extends RuntimeException {
    public SchoolFilledByClassesException(String message) {
        super(message);
    }

    public SchoolFilledByClassesException(String message, Throwable cause) {
        super(message, cause);
    }
}
