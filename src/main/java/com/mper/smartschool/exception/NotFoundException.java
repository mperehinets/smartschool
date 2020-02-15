package com.mper.smartschool.exception;

public class NotFoundException extends RuntimeException {

    private final Object byWhat;

    public NotFoundException(String message, Object byWhat) {
        super(message);
        this.byWhat = byWhat;
    }

    public Object getByWhat() {
        return byWhat;
    }
}
